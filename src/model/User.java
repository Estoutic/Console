package model;

import filter.SpamFilter;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userName;
    private final List<Message> inbox;
    private final List<Message> outbox;
    private final List<Message> spam;
    private SpamFilter spamfilter;

    public User(String userName) {
        this.userName = userName;
        this.inbox = new ArrayList<>();
        this.outbox = new ArrayList<>();
        this.spam = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public List<Message> getInbox() {
        return new ArrayList<>(inbox);
    }

    public List<Message> getOutbox() {
        return new ArrayList<>(outbox);
    }

    public List<Message> getSpam() {
        return new ArrayList<>(spam);
    }

    public void setSpamFilter(SpamFilter spamfilter) {
        this.spamfilter = spamfilter;
    }

    public void sendMessage(String caption, String text, User receiver) {
        Message message = new Message(caption, text, this, receiver);
        outbox.add(message);

        if (receiver != null) {
            if (receiver.spamfilter != null && receiver.spamfilter.isSpam(message)) {
                receiver.spam.add(message);
            } else {
                receiver.inbox.add(message);
            }
        }
    }

}
