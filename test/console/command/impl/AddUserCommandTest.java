package console.command.impl;

import console.command.impl.AddUserCommand;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.UserStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AddUserCommandTest {

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
    void testAddNewUser() {
        String input = "TestUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        AddUserCommand command = new AddUserCommand(scanner, userStorage);
        command.execute();

        assertTrue(userStorage.hasUser("TestUser"));
        assertEquals(1, userStorage.getUserCount());
        assertTrue(outputStream.toString().contains("User 'TestUser' added"));
    }

    @Test
    void testAddExistingUser() {
        userStorage.addUser(new User("ExistingUser"));

        String input = "ExistingUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        AddUserCommand command = new AddUserCommand(scanner, userStorage);
        command.execute();

        assertEquals(1, userStorage.getUserCount());
        assertTrue(outputStream.toString().contains("User 'ExistingUser' already exists"));
    }

    @Test
    void testGetDescription() {
        AddUserCommand command = new AddUserCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
