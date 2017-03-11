package ua.goodnews.services.bayes;

import ua.goodnews.dto.FeedEntry;
import ua.goodnews.model.Category;

import java.util.Collection;
import java.util.Set;

/**
 * Created by acidum on 2/9/17.
 */
public interface BayesClassifier {
    void classify(FeedEntry entry, Collection<Category> categories);
}
