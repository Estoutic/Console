package utils;

import model.Message;
import model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckUtilsTest {

    @Test
    void testValidMessage() {
        User sender = new User("Vladimir");
        User receiver = new User("Egor");
        Message message = new Message("Заголовок", "Привет, как дела?", sender, receiver);

        assertTrue(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testNullMessage() {
        assertFalse(CheckUtils.checkValidMessage(null));
    }

    @Test
    void testNullSender() {
        User receiver = new User("Egor");
        Message message = new Message("Заголовок", "Привет, как дела?", null, receiver);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testNullReceiver() {
        User sender = new User("Vladimir");
        Message message = new Message("Заголовок", "Привет, как дела?", sender, null);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testSenderIsSameAsReceiver() {
        User user = new User("Vladimir");
        Message message = new Message("Заголовок", "Привет, как дела?", user, user);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testEmptyText() {
        User sender = new User("Vladimir");
        User receiver = new User("Egor");
        Message message = new Message("Заголовок", "", sender, receiver);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testNullCaption() {
        User sender = new User("Vladimir");
        User receiver = new User("Egor");
        Message message = new Message(null, "Привет, как дела?", sender, receiver);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testTextTooLong() {
        User sender = new User("Vladimir");
        User receiver = new User("Egor");
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 2001; i++) {
            longText.append("a");
        }
        Message message = new Message("Заголовок", longText.toString(), sender, receiver);

        assertFalse(CheckUtils.checkValidMessage(message));
    }

    @Test
    void testCaptionTooLong() {
        User sender = new User("Vladimir");
        User receiver = new User("Egor");
        StringBuilder longCaption = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            longCaption.append("a");
        }
        Message message = new Message(longCaption.toString(), "Привет, как дела?", sender, receiver);

        assertFalse(CheckUtils.checkValidMessage(message));
    }
}