package filter.impl;

import exceptions.MessageInvalidException;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

public class CompositeSpamFilter implements SpamFilter {
    private final List<SpamFilter> filterList;

    public CompositeSpamFilter() {
        filterList = new ArrayList<>();
    }

    public void addFilter(SpamFilter filter) {
        filterList.add(filter);
    }

    @Override
    public boolean isSpam(Message message) {

        if (!CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }

        for (int i = 0; i < filterList.size(); i++) {
            SpamFilter currentFilter = filterList.get(i);
            if (currentFilter.isSpam(message)) {
                return true;
            }
        }

        return false;
    }
}