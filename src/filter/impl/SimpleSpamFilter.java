package filter.impl;

import exceptions.MessageInvalidException;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleSpamFilter implements SpamFilter {

    public static final Pattern PATTERN = Pattern.compile("//bspam//b", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isSpam(Message message) {
        if ( CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }
        return PATTERN.matcher(message.getText()).find() ||
                PATTERN.matcher(message.getCaption()).find();

    }
}
