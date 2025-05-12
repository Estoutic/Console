package filter;

import filter.impl.SimpleSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleSpamFilterTest {
    SimpleSpamFilter filter;
    User user1, user2;

    @BeforeEach
    void setup() {
        filter = new SimpleSpamFilter();
        user1 = new User("Vladimir");
        user2 = new User("Egor");
    }

    @Test
    void testNormal() {
        Message msg = new Message(
                "Привет",
                "Как дела?",
                user1,
                user2
        );

        boolean result = filter.isSpam(msg);
        assertFalse(result);
    }

    @Test
    void testSpamInText() {
        Message msg = new Message(
                "Тема",
                "Это spam сообщение",
                user1,
                user2
        );

        boolean result = filter.isSpam(msg);
        assertTrue(result);
    }

    @Test
    void testSpamInCaption() {
        Message msg = new Message(
                "Spam alert",
                "Обычный текст",
                user1,
                user2
        );

        boolean result = filter.isSpam(msg);
        assertTrue(result);
    }

    @Test
    void testCase() {
        Message msg = new Message(
                "Тема",
                "Это SPAM сообщение",
                user1,
                user2
        );

        boolean result = filter.isSpam(msg);
        assertTrue(result);
    }
}