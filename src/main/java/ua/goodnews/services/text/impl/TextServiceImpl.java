package ua.goodnews.services.text.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.model.Category;
import ua.goodnews.model.Text;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.terms.TermAccumulator;
import ua.goodnews.services.text.TextDataSetDecider;
import ua.goodnews.services.text.TextService;

/**
 * Created by acidum on 3/26/17.
 */
@Service
public class TextServiceImpl implements TextService {

    @Autowired
    private TextDataSetDecider textDataSetDecider;

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TermAccumulator termAccumulator;

    @Override
    public void saveText(String content, Category category) {
        Text.DataSet dataSet = textDataSetDecider.calculateDataSet(content, category);

        Text text = new Text(content, category);
        text.setDataSet(dataSet);
        textRepository.save(text);

        if(Text.DataSet.LEARNING.equals(text.getDataSet())) {
            termAccumulator.accumulate(text);
        }
    }
}
