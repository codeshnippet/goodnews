package ua.goodnews.processors.impl;

import org.springframework.stereotype.Component;
import ua.goodnews.processors.TextProcessor;

/**
 * Created by acidum on 1/27/17.
 */

@Component
public class RegexpFilter implements TextProcessor {

    @Override
    public String process(String text) {
        return text.replaceAll("[^a-zA-Z\\s]","");
    }
}
