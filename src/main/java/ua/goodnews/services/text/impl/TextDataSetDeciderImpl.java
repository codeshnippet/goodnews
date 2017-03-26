package ua.goodnews.services.text.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.model.Category;
import ua.goodnews.model.Text;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.text.TextDataSetDecider;

/**
 * Created by acidum on 3/26/17.
 */

@Service
public class TextDataSetDeciderImpl implements TextDataSetDecider {

    public static final double TEST_TO_LEARNING_RATIO = 0.1;

    @Autowired
    private TextRepository textRepository;

    @Override
    public Text.DataSet calculateDataSet(String text, Category category) {
        double learningTextsCount = (double)textRepository.countByCategoryAndDataSet(category, Text.DataSet.LEARNING);
        double testTextsCount = (double)textRepository.countByCategoryAndDataSet(category, Text.DataSet.TEST);
        if(testTextsCount + learningTextsCount == 0) {
            return Text.DataSet.LEARNING;
        }

        double testToLearningRatio = testTextsCount / (testTextsCount + learningTextsCount);
        if (testToLearningRatio > TEST_TO_LEARNING_RATIO) {
            return Text.DataSet.LEARNING;
        }
        return Text.DataSet.TEST;
    }
}
