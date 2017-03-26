package ua.goodnews.analyzers;

import ua.goodnews.model.Filter;

/**
 * Created by acidum on 3/26/17.
 */
public interface EfficiencyAnalyzer {
    double calculateFilterEfficiency(Filter filter);
}
