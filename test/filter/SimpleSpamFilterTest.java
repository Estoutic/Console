package filter;

import filter.impl.SimpleSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleSpamFilterTest {

    private SimpleSpamFilter filter;
    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        filter = new SimpleSpamFilter();
        sender = new User("Vladimir");
        receiver = new User("Egor");
    }

    @Test
    void testNonSpamMessage() {
        Message message = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(filter.isSpam(message));
    }

    @Test
    void testSpamInText() {
        Message message = new Message("Заголовок", "Это сообщение содержит слово spam", sender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testSpamInCaption() {
        Message message = new Message("SPAM Alert", "Обычное сообщение", sender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testCaseInsensitivity() {
        Message message = new Message("Заголовок", "Это сообщение содержит слово SpAm", sender, receiver);
        assertTrue(filter.isSpam(message));
    }
}