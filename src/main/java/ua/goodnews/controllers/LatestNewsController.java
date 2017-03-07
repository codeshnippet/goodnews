package ua.goodnews.controllers;

import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.goodnews.dto.NewsEntry;
import ua.goodnews.model.Category;
import ua.goodnews.model.Filter;
import ua.goodnews.services.bayes.BayesClassifier;
import ua.goodnews.services.rss.FeedReader;
import ua.goodnews.services.terms.TermAccumulator;

import java.util.List;

/**
 * Created by acidum on 1/20/17.
 */

@Controller
public class LatestNewsController {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private TermAccumulator termAccumulator;

    @Autowired
    private BayesClassifier bayesClassifier;

    @Autowired
    private FeedReader feedReader;

    @RequestMapping(value="/rss", method = RequestMethod.GET)
    public @ResponseBody
    List<NewsEntry> getFeed(@RequestParam(value="url", required=true) String url,
                            @RequestParam(value="filter", required=true) Filter filter) {
        List<SyndEntry> entries = feedReader.read(url);

        List<NewsEntry> newsEntries = (List<NewsEntry>)conversionService.convert(entries,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(SyndEntry.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(NewsEntry.class)));

        for(NewsEntry entry: newsEntries){
            Category result = bayesClassifier.classify(entry.description, filter.getCategories());
            entry.categoryName = result.getName();
        }

        return newsEntries;
    }

    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public void teach(String text, Category category){
        termAccumulator.accumulate(text, category);
    }
}
