package ua.goodnews.processors.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.goodnews.processors.TextProcessor;

/**
 * Created by acidum on 2/19/17.
 */

@Component
public class ToLowerCaseConverter implements TextProcessor {
    @Override
    public String process(String text) {
        return text.toLowerCase();
    }
}
