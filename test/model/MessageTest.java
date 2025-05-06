package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private User sender;
    private User receiver;
    private Message message;

    @BeforeEach
    void setUp() {
        sender = new User("Valdimir");
        receiver = new User("Egor");
        message = new Message("Заголовок", "Привет, как дела?", sender, receiver);
    }

    @Test
    void testGetCaption() {
        assertEquals("Заголовок", message.getCaption());
    }

    @Test
    void testGetText() {
        assertEquals("Привет, как дела?", message.getText());
    }

    @Test
    void testGetSender() {
        assertEquals(sender, message.getSender());
    }

    @Test
    void testGetReceiver() {
        assertEquals(receiver, message.getReceiver());
    }
}