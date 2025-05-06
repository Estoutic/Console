package model;

import filter.impl.SimpleSpamFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        sender = new User("Vladimir");
        receiver = new User("Egor");
    }

    @Test
    void testGetUserName() {
        assertEquals("Vladimir", sender.getUserName());
        assertEquals("Egor", receiver.getUserName());
    }

    @Test
    void testSendMessage() {
        sender.sendMessage("Заголовок", "Привет, как дела?", receiver);

        assertEquals(1, sender.getOutbox().size());

        assertEquals(1, receiver.getInbox().size());
        assertEquals(0, receiver.getSpam().size());

        Message sentMessage = sender.getOutbox().get(0);
        Message receivedMessage = receiver.getInbox().get(0);

        assertEquals("Заголовок", sentMessage.getCaption());
        assertEquals("Привет, как дела?", sentMessage.getText());
        assertEquals(sender, sentMessage.getSender());
        assertEquals(receiver, sentMessage.getReceiver());

        assertEquals(sentMessage.getCaption(), receivedMessage.getCaption());
        assertEquals(sentMessage.getText(), receivedMessage.getText());
    }

    @Test
    void testSendMessageWithSpamFilter() {
        SimpleSpamFilter spamFilter = new SimpleSpamFilter();
        receiver.setSpamFilter(spamFilter);

        sender.sendMessage("Заголовок", "Привет, как дела?", receiver);

        sender.sendMessage("Spam Alert", "This is spam message", receiver);

        assertEquals(1, receiver.getInbox().size());
        assertEquals(1, receiver.getSpam().size());

        Message spamMessage = receiver.getSpam().get(0);
        assertEquals("Spam Alert", spamMessage.getCaption());
    }
}