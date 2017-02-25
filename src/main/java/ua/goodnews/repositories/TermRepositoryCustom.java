package ua.goodnews.repositories;

import ua.goodnews.model.Category;
import ua.goodnews.model.Filter;
import ua.goodnews.model.Term;

import java.util.Collection;
import java.util.Set;

/**
 * Created by acidum on 1/28/17.
 */
public interface TermRepositoryCustom {
    long getTermCountByCategoryAndThreshold(Category category);
    Term findTermByWordAndCategoryAndThreshold(String word, Category category);
}
