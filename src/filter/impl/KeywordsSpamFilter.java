package filter.impl;

import exceptions.*;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.*;

public class KeywordsSpamFilter implements SpamFilter {

    private List<String> keywords;

    public KeywordsSpamFilter() {
        this.keywords = new ArrayList<>();
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = new ArrayList<>();

        if (keywords != null) {
            for (String keyword : keywords) {
                if (keyword != null && !keyword.isEmpty()) {
                    this.keywords.add(keyword.toLowerCase());
                }
            }
        }
    }

    @Override
    public boolean isSpam(Message message) {
        if (!CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }

        if (keywords.isEmpty()) {
            return false;
        }

        String text = message.getText().toLowerCase();
        String caption = message.getCaption().toLowerCase();

        for (String keyword : keywords) {
            if (containsWord(text, keyword) || containsWord(caption, keyword)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsWord(String text, String word) {
        String[] words = text.split("[\\s,.!?;:()\\[\\]{}\"']+");
        for (String w : words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }
}