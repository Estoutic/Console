package filter.impl;

import exceptions.MessageInvalidException;
import filter.SpamFilter;
import model.Message;
import utils.CheckUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeSpamFilter implements SpamFilter {

    public CompositeSpamFilter() {
        this.filters = new ArrayList<>();
    }

    private final List<SpamFilter> filters;

    public void addFilters(SpamFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    public void addFilter(SpamFilter filter) {
        this.filters.add(filter);
    }


    @Override
    public boolean isSpam(Message message) {

        if ( CheckUtils.checkValidMessage(message)) {
            throw new MessageInvalidException();
        }

        for (SpamFilter filter : filters) {
            if (filter.isSpam(message)) {
                return true;
            }
        }
        return false;
    }
}
