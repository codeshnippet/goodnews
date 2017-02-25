package ua.goodnews.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goodnews.processors.impl.RegexpFilter;
import ua.goodnews.processors.impl.PopularTermsFilter;
import ua.goodnews.processors.impl.ToLowerCaseConverter;

/**
 * Created by acidum on 2/19/17.
 */

@Service
public class TextPipelineManager {

    @Autowired
    private ToLowerCaseConverter toLowerCaseConverter;

    @Autowired
    private RegexpFilter regexpFilter;

    @Autowired
    private PopularTermsFilter popularTermsFilter;

    public String process(String text){
        text = regexpFilter.process(text);
        text = toLowerCaseConverter.process(text);
        text = popularTermsFilter.process(text);
        return text;
    }

}
