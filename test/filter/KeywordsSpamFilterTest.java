package filter;

import filter.impl.KeywordsSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class KeywordsSpamFilterTest {

    private KeywordsSpamFilter filter;
    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        filter = new KeywordsSpamFilter();
        sender = new User("Vladimir");
        receiver = new User("Egor");

        filter.setKeywords(Arrays.asList("гараж", "продам", "купить"));
    }

    @Test
    void testNonSpamMessage() {
        Message message = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(filter.isSpam(message));
    }

    @Test
    void testSpamKeywordInText() {
        Message message = new Message("Заголовок", "Продам велосипед", sender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testSpamKeywordInCaption() {
        Message message = new Message("Гараж в центре", "Обычное сообщение", sender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testMultipleKeywords() {
        Message message = new Message("Продам гараж", "Хочу купить машину", sender, receiver);
        assertTrue(filter.isSpam(message));
    }

    @Test
    void testEmptyKeywordsList() {
        filter.setKeywords(Arrays.asList());
        Message message = new Message("Продам гараж", "Хочу купить машину", sender, receiver);
        assertFalse(filter.isSpam(message));
    }
}