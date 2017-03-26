package ua.goodnews.analyzers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.analyzers.EfficiencyAnalyzer;
import ua.goodnews.model.Category;
import ua.goodnews.model.Filter;
import ua.goodnews.model.Text;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.bayes.BayesClassifier;

import java.util.Collection;

/**
 * Created by acidum on 3/26/17.
 */
@Service
public class EfficiencyAnalyzerImpl implements EfficiencyAnalyzer {

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private BayesClassifier bayesClassifier;

    @Override
    public double calculateFilterEfficiency(Filter filter) {
        Collection<Text> testTexts = textRepository.findTextByDataSet(Text.DataSet.TEST);

        if(testTexts.isEmpty()){
            return 0.0;
        }

        double correctCount = 0.0;
        for(Text testText: testTexts){
            Category resultCategory = bayesClassifier.classify(testText.getContent(), filter.getCategories());
            if(resultCategory.getId() == testText.getCategory().getId()){
                correctCount += 1.0;
            }
        }

        return correctCount / (double)testTexts.size();
    }
}
