package utils;

import model.Message;

public class CheckUtils {

    public static boolean checkValidMessage(Message message) {
        if (message == null) {
            return false;
        }

        if (message.getSender() == null || message.getReceiver() == null) {
            return false;
        }

        if (message.getSender().equals(message.getReceiver())) {
            return false;
        }

        String text = message.getText();
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        String caption = message.getCaption();
        if (caption == null) {
            return false;
        }

        if (text.length() > 2000) {
            return false;
        }

        if (caption.length() > 100) {
            return false;
        }

        return true;
    }
}
