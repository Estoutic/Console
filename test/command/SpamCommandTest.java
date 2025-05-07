package command;

import console.command.impl.SpamCommand;
import filter.impl.SimpleSpamFilter;
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

public class SpamCommandTest {

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

        SimpleSpamFilter filter = new SimpleSpamFilter();
        user.setSpamFilter(filter);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testSpamEmpty() {
        String input = "TestUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SpamCommand command = new SpamCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("No messages in spam folder"));
    }

    @Test
    void testSpamWithMessages() {
        sender.sendMessage("Spam Subject", "This contains the word spam", user);

        String input = "TestUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SpamCommand command = new SpamCommand(scanner, userStorage);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Spam Subject"));
        assertTrue(output.contains("This contains the word spam"));
    }

    @Test
    void testSpamUserNotFound() {
        String input = "NonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SpamCommand command = new SpamCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testGetDescription() {
        SpamCommand command = new SpamCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
