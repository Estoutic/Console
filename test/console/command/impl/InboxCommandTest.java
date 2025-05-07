package console.command.impl;

import console.command.impl.InboxCommand;
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

public class InboxCommandTest {

    private UserStorage userStorage;
    private User user;
    private User sender;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
        user = new User("TestUser");
        sender = new User("Sender");
        userStorage.addUser(user);
        userStorage.addUser(sender);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testInboxEmpty() {
        String input = "TestUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        InboxCommand command = new InboxCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("No messages in inbox"));
    }

    @Test
    void testInboxWithMessages() {
        sender.sendMessage("Test Subject", "Test Message", user);

        String input = "TestUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        InboxCommand command = new InboxCommand(scanner, userStorage);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Test Subject"));
        assertTrue(output.contains("Test Message"));
    }

    @Test
    void testInboxUserNotFound() {
        String input = "NonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        InboxCommand command = new InboxCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testGetDescription() {
        InboxCommand command = new InboxCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
