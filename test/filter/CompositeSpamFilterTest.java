package filter;

import filter.impl.CompositeSpamFilter;
import filter.impl.KeywordsSpamFilter;
import filter.impl.SimpleSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompositeSpamFilterTest {
    CompositeSpamFilter filter;
    SimpleSpamFilter simple;
    KeywordsSpamFilter keywords;
    User sender;
    User recipient;

    @BeforeEach
    void setup() {
        filter = new CompositeSpamFilter();
        simple = new SimpleSpamFilter();
        keywords = new KeywordsSpamFilter();

        sender = new User("Vladimir");
        recipient = new User("Egor");

        keywords.setKeywords(java.util.Arrays.asList("гараж", "продам"));
    }

    @Test
    void testEmptyFilter() {
        Message msg = new Message("Привет", "Как дела?", sender, recipient);

        assertFalse(filter.isSpam(msg));
    }

    @Test
    void testSimpleFilter() {
        filter.addFilter(simple);

        Message normal = new Message("Привет", "Обычное сообщение", sender, recipient);
        assertFalse(filter.isSpam(normal));

        Message spam = new Message("Тема", "Это spam сообщение", sender, recipient);
        assertTrue(filter.isSpam(spam));
    }

    @Test
    void testKeywordsFilter() {
        filter.addFilter(keywords);

        Message spam = new Message("Объявление", "Продам велосипед", sender, recipient);
        assertTrue(filter.isSpam(spam));

        Message normal = new Message("Привет", "Как дела?", sender, recipient);
        assertFalse(filter.isSpam(normal));
    }

    @Test
    void testBothFilters() {
        filter.addFilter(simple);
        filter.addFilter(keywords);

        Message spam1 = new Message("Тема", "Это spam", sender, recipient);
        assertTrue(filter.isSpam(spam1));

        Message spam2 = new Message("Тема", "Продам машину", sender, recipient);
        assertTrue(filter.isSpam(spam2));

        Message normal = new Message("Привет", "Как дела?", sender, recipient);
        assertFalse(filter.isSpam(normal));
    }
}