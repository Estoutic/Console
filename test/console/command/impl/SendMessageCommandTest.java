package console.command.impl;

import console.command.impl.SendMessageCommand;
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

public class SendMessageCommandTest {

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
    void testSendMessage() {
        String input = "Sender\nReceiver\nTest Subject\nTest Message\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SendMessageCommand command = new SendMessageCommand(scanner, userStorage);
        command.execute();

        assertEquals(1, sender.getOutbox().size());
        assertEquals(1, receiver.getInbox().size());
        assertTrue(outputStream.toString().contains("Message sent"));
    }

    @Test
    void testSenderNotFound() {
        String input = "NonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SendMessageCommand command = new SendMessageCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testReceiverNotFound() {
        String input = "Sender\nNonExistentUser\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        SendMessageCommand command = new SendMessageCommand(scanner, userStorage);
        command.execute();

        assertTrue(outputStream.toString().contains("Error: User not found"));
    }

    @Test
    void testGetDescription() {
        SendMessageCommand command = new SendMessageCommand(new Scanner(""), userStorage);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }
}
