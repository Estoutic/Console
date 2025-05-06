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

    private CompositeSpamFilter compositeFilter;
    private SimpleSpamFilter simpleFilter;
    private KeywordsSpamFilter keywordsFilter;
    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        compositeFilter = new CompositeSpamFilter();
        simpleFilter = new SimpleSpamFilter();
        keywordsFilter = new KeywordsSpamFilter();
        sender = new User("Vladimir");
        receiver = new User("Egor");

        keywordsFilter.setKeywords(java.util.Arrays.asList("гараж", "продам"));
    }

    @Test
    void testEmptyCompositeFilter() {
        Message message = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(compositeFilter.isSpam(message));
    }

    @Test
    void testSimpleFilterOnly() {
        compositeFilter.addFilter(simpleFilter);

        Message normalMessage = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(compositeFilter.isSpam(normalMessage));

        Message spamMessage = new Message("Заголовок", "Сообщение со словом spam", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamMessage));
    }

    @Test
    void testKeywordsFilterOnly() {
        compositeFilter.addFilter(keywordsFilter);

        Message normalMessage = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(compositeFilter.isSpam(normalMessage));

        Message spamMessage = new Message("Заголовок", "Продам велосипед", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamMessage));
    }

    @Test
    void testMultipleFilters() {
        compositeFilter.addFilter(simpleFilter);
        compositeFilter.addFilter(keywordsFilter);

        Message normalMessage = new Message("Заголовок", "Обычное сообщение", sender, receiver);
        assertFalse(compositeFilter.isSpam(normalMessage));

        Message spamForSimple = new Message("Заголовок", "Сообщение со словом spam", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamForSimple));

        Message spamForKeywords = new Message("Заголовок", "Продам велосипед", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamForKeywords));
    }

    @Test
    void testAddFilters() {
        compositeFilter.addFilters(simpleFilter, keywordsFilter);

        Message spamForSimple = new Message("Заголовок", "Сообщение со словом spam", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamForSimple));

        Message spamForKeywords = new Message("Заголовок", "Продам велосипед", sender, receiver);
        assertTrue(compositeFilter.isSpam(spamForKeywords));
    }
}