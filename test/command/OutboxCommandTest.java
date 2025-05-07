package command;

import console.command.impl.OutboxCommand;
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

public class OutboxCommandTest {

    private UserStorage userStorage;
    private User sender;
    private User receiver;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
        sender = new User("Sender");
        receiver = new User("Receiver");
        userStorage.addUser(sender);
        userStorage.addUser(receiver);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testOutboxEmpty() {
        String input = "Sender\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        OutboxCommand command = new OutboxCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("No messages in outbox"));
    }

    @Test
    void testOutboxWithMessages() {
        sender.sendMessage("Test Subject", "Test Message", receiver);

        String input = "Sender\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        OutboxCommand command = new OutboxCommand(scanner, userStorage);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Test Subject"));
        assertTrue(output.contains("Test Message"));
        assertTrue(output.contains("To: Receiver"));
    }

    @Test
    void testOutboxUserNotFound() {
        String input = "NonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        OutboxCommand command = new OutboxCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testGetDescription() {
        OutboxCommand command = new OutboxCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
