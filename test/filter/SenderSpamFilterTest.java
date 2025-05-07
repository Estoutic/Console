package filter;

import filter.impl.SenderSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SenderSpamFilterTest {

    private SenderSpamFilter filter;
    private User spamSender;
    private User normalSender;
    private User receiver;

    @BeforeEach
    void setUp() {
        filter = new SenderSpamFilter();
        spamSender = new User("SpamUser");
        normalSender = new User("NormalUser");
        receiver = new User("Receiver");

        filter.addUser(spamSender.getUserName());
    }

    @Test
    void testSpamFromSpamSender() {
        Message message = new Message("Заголовок", "Сообщение от спам-отправителя", spamSender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testMessageFromNormalSender() {
        Message message = new Message("Заголовок", "Сообщение от обычного отправителя", normalSender, receiver);
        assertFalse(filter.isSpam(message));
    }

    @Test
    void testAddMultipleSpamSenders() {
        User anotherSpamSender = new User("AnotherSpamUser");
        filter.addUser(anotherSpamSender.getUserName());

        Message message1 = new Message("Заголовок", "Сообщение от первого спам-отправителя", spamSender, receiver);
        Message message2 = new Message("Заголовок", "Сообщение от второго спам-отправителя", anotherSpamSender, receiver);

        assertTrue(filter.isSpam(message1));
        assertTrue(filter.isSpam(message2));
    }
}
