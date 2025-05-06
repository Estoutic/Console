package filter.impl;

import exceptions.*;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.*;
import java.util.regex.Pattern;

public class KeywordsSpamFilter implements SpamFilter {

    private final Map<String, Pattern> keywordPatterns;

    public KeywordsSpamFilter() {
        keywordPatterns = new HashMap<>();
    }

    public void setKeywords(List<String> keywords) {
        keywordPatterns.clear();

        if (keywords != null) {
            for (String keyword : keywords) {
                if (keyword != null && !keyword.isEmpty()) {

                    String lowerKeyword = keyword.toLowerCase();
                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(lowerKeyword) + "\\b",
                            Pattern.CASE_INSENSITIVE);
                    this.keywordPatterns.put(lowerKeyword, pattern);
                }
            }
        }
    }

    @Override
    public boolean isSpam(Message message) {
        if ( CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }
        if (keywordPatterns.isEmpty()) {
            return false;
        }

        String text = message.getText();
        String caption = message.getCaption();
        for (Map.Entry<String, Pattern> entry : keywordPatterns.entrySet()) {
            String keyword = entry.getKey();
            Pattern pattern = entry.getValue();

            if (text.contains(keyword) || caption.contains(keyword)) {
                if (pattern.matcher(caption).find() || pattern.matcher(text).find()) {
                    return true;
                }
            }
        }
        return false;
    }
}
