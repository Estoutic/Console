package filter;

import model.Message;

public interface SpamFilter {
    boolean isSpam(Message message);
}
