package console.command.impl;

import console.command.impl.SetFilterCommand;
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

public class SetFilterCommandTest {

    private UserStorage userStorage;
    private User user;
    private User spamSender;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
        user = new User("TestUser");
        spamSender = new User("SpamSender");
        userStorage.addUser(user);
        userStorage.addUser(spamSender);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testUserNotFound() {
        String input = "NonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SetFilterCommand command = new SetFilterCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testInvalidFilterType() {
        String input = "TestUser\ninvalidFilter\ndone\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SetFilterCommand command = new SetFilterCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Unknown filter type"));
    }

    @Test
    void testGetDescription() {
        SetFilterCommand command = new SetFilterCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
