package ua.goodnews.services.terms;

import ua.goodnews.model.Category;

/**
 * Created by acidum on 2/25/17.
 */
public interface TermAccumulator {

    void accumulate(String content, Category category);
}
