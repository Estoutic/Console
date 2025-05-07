package model;

import filter.impl.SimpleSpamFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User sender;
    User receiver;

    @BeforeEach
    void setup() {
        sender = new User("Вася");
        receiver = new User("Петя");
    }

    @Test
    void testName() {
        assertEquals("Вася", sender.getUserName());
        assertEquals("Петя", receiver.getUserName());
    }

    @Test
    void testSendMessage() {
        sender.sendMessage("Привет", "Как твои дела?", receiver);

        int size1 = sender.getOutbox().size();
        assertEquals(1, size1);

        int size2 = receiver.getInbox().size();
        assertEquals(1, size2);

        assertEquals(0, receiver.getSpam().size());

        Message sent = sender.getOutbox().get(0);
        assertEquals("Привет", sent.getCaption());
        assertEquals("Как твои дела?", sent.getText());

        Message received = receiver.getInbox().get(0);
        assertEquals("Привет", received.getCaption());
        assertEquals("Как твои дела?", received.getText());
    }

    @Test
    void testSpamFilter() {
        SimpleSpamFilter filter = new SimpleSpamFilter();
        receiver.setSpamFilter(filter);

        sender.sendMessage("Привет", "Обычное сообщение", receiver);

        sender.sendMessage("Тема", "Это spam сообщение", receiver);

        assertEquals(1, receiver.getInbox().size());

        assertEquals(1, receiver.getSpam().size());

        Message spam = receiver.getSpam().get(0);
        assertEquals("Тема", spam.getCaption());
    }
}