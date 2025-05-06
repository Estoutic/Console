package filter.impl;

import exceptions.MessageInvalidException;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepetitionSpamFilter implements SpamFilter {

    private final int maxRepetitions;

    public RepetitionSpamFilter(int maxRepetitions) {
        if (maxRepetitions <= 0) {
            throw new IllegalArgumentException("Maximum repetitions must be a positive number");
        }
        this.maxRepetitions = maxRepetitions;
    }

    @Override
    public boolean isSpam(Message message) {
        if (!CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }

        String text = message.getText();

        Map<String, Integer> wordCounts = new HashMap<>();

        Pattern pattern = Pattern.compile("\\p{L}+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(text.toLowerCase());

        while (matcher.find()) {
            String word = matcher.group();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

            if (wordCounts.get(word) > maxRepetitions) {
                return true;
            }
        }

        return false;
    }
}