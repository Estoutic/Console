package filter;

import filter.impl.RepetitionSpamFilter;
import model.Message;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepetitionSpamFilterTest {

    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        sender = new User("Vasya");
        receiver = new User("Petya");
    }

    @Test
    void testInvalidMaxRepetitionsParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RepetitionSpamFilter(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new RepetitionSpamFilter(-1);
        });
    }

    @Test
    void testNoRepetitions() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(2);
        Message message = new Message("Заголовок", "Это простое сообщение без повторений", sender, receiver);

        assertFalse(filter.isSpam(message));
    }

    @Test
    void testRepetitionsUnderLimit() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(2);
        Message message = new Message("Заголовок", "Привет привет, как дела?", sender, receiver);

        assertFalse(filter.isSpam(message));
    }

    @Test
    void testRepetitionsAtLimit() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(3);
        Message message = new Message("Заголовок", "привет привет привет, как дела?", sender, receiver);

        assertFalse(filter.isSpam(message));
    }

    @Test
    void testRepetitionsOverLimit() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(2);
        Message message = new Message("Заголовок", "привет привет привет, как дела?", sender, receiver);

        assertTrue(filter.isSpam(message));
    }

    @Test
    void testCaseInsensitivity() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(2);
        Message message = new Message("Заголовок", "Привет ПРИВЕТ привет, как дела?", sender, receiver);

        assertTrue(filter.isSpam(message));
    }
}