package ua.goodnews.services.terms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.model.Category;
import ua.goodnews.model.Term;
import ua.goodnews.model.Text;
import ua.goodnews.processors.TextPipelineManager;
import ua.goodnews.repositories.TermRepository;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.terms.TermAccumulator;

/**
 * Created by acidum on 2/25/17.
 */

@Service
public class TermAccumulatorImpl implements TermAccumulator {

    @Autowired
    private TextPipelineManager textPipelineManager;

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TermRepository termRepository;

    @Override
    public void accumulate(Text text) {
        String content = textPipelineManager.process(text.getContent());

        for(String word: content.split(" ")) {
            Term existingTerm = termRepository.findTermByWordAndTextCategory(word, text.getCategory());
            if (existingTerm != null){
                existingTerm.incrementCount();
                termRepository.save(existingTerm);
            }else {
                termRepository.save(new Term(text, word));
            }
        }
    }
}
