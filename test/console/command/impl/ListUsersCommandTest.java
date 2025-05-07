package console.command.impl;

import console.command.impl.ListUsersCommand;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.UserStorage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ListUsersCommandTest {

    private UserStorage userStorage;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testListNoUsers() {
        ListUsersCommand command = new ListUsersCommand(new Scanner(""), userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("No users found"));
    }

    @Test
    void testListWithUsers() {
        userStorage.addUser(new User("Vladimir"));
        userStorage.addUser(new User("Egor"));

        ListUsersCommand command = new ListUsersCommand(new Scanner(""), userStorage);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("* Vladimir"));
        assertTrue(output.contains("* Egor"));
        assertTrue(output.contains("Total: 2 users"));
    }

    @Test
    void testGetDescription() {
        ListUsersCommand command = new ListUsersCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}