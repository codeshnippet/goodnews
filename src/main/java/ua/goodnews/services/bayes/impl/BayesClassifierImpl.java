package ua.goodnews.services.bayes.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.dto.FeedEntry;
import ua.goodnews.model.Category;
import ua.goodnews.model.Term;
import ua.goodnews.processors.TextPipelineManager;
import ua.goodnews.repositories.TermRepository;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.bayes.BayesClassifier;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by acidum on 1/20/17.
 */

@Service
public class BayesClassifierImpl implements BayesClassifier {

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TextPipelineManager textPipelineManager;

    @Override
    public void classify(FeedEntry entry, Collection<Category> categories) {
        String text = textPipelineManager.process(entry.description);

        long vocabularySize  = termRepository.count();

        double maxProbability = 0.0;
        Category maxCategory = null;
        for(Category category: categories){
            double categoryProb = getCategoryRatio(category);
            double probability = calculateProbability(text, category, categoryProb, vocabularySize);
            if(maxProbability <= probability){
                maxProbability = probability;
                maxCategory = category;
            }
        }

        entry.category = maxCategory;
    }

    private double calculateProbability(String text, Category category, double categoryProb, long vocabularySize) {
        long categoryCount = termRepository.getTermCountByCategoryAndThreshold(category);

        BigDecimal probProduct = new BigDecimal(1.0);

        for(String word: text.split(" ")){
            double termCount = 0;
            Term foundTerm = termRepository.findTermByWordAndCategoryAndThreshold(word, category);
            if(foundTerm != null) {
                termCount = (double)foundTerm.getCount();
            }

            double product = (termCount + 1.0) / ((double)categoryCount + (double)vocabularySize);
            probProduct = probProduct.multiply(new BigDecimal(product));
        }

        return probProduct.doubleValue() * categoryProb;
    }

    private double getCategoryRatio(Category category) {
        long countByCategory =  textRepository.countByCategory(category);
        long allCount =  textRepository.count();
        return (double)countByCategory/(double)allCount;
    }

}
