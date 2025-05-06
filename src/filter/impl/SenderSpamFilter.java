package filter.impl;

import exceptions.MessageInvalidException;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.HashSet;
import java.util.Set;

public class SenderSpamFilter implements SpamFilter {

    private final Set<String> users;

    public SenderSpamFilter() {
        this.users = new HashSet<>();
    }

    public void addUser(String user) {
        this.users.add(user);
    }

    @Override
    public boolean isSpam(Message message) {

        if (!CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }
        return users.contains(message.getSender().getUserName());
    }
}
