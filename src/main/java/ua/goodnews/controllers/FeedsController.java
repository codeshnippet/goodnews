package ua.goodnews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.goodnews.dto.FeedEntry;
import ua.goodnews.model.Category;
import ua.goodnews.model.Feed;
import ua.goodnews.repositories.CategoryRepository;
import ua.goodnews.repositories.FilterRepository;
import ua.goodnews.services.bayes.BayesClassifier;
import ua.goodnews.services.rss.FeedReader;
import ua.goodnews.services.terms.TermAccumulator;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class FeedsController {

    @Autowired
    private TermAccumulator termAccumulator;

    @Autowired
    private BayesClassifier bayesClassifier;

    @Autowired
    private FeedReader feedReader;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value="/filters/{filterId}/feeds/{feedId}", method = RequestMethod.GET)
    public @ResponseBody
    Feed getFeed(@PathVariable(value="filterId", required = true) Long filterId,
                 @PathVariable(value="feedId", required = true) Long feedId){

        Feed resultFeed = filterRepository.
                findOne(filterId).
                getFeeds().
                stream().
                filter(feed -> feedId == feed.getId()).
                collect(singletonCollector());

        List<FeedEntry> feedEntries = feedReader.read(resultFeed.getUrl());

        feedEntries.stream().forEach(entry -> bayesClassifier.classify(entry, resultFeed.getFilter().getCategories()));

        resultFeed.setEntries(feedEntries);

        return resultFeed;
    }

    @RequestMapping(value = "/category/{categoryId}/mark", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void teach(@RequestBody String text, @PathVariable(value="categoryId", required = true)Long categoryId){
        Category category = categoryRepository.findOne(categoryId);
        termAccumulator.accumulate(text, category);
    }

    public static <T> Collector<T, ?, T> singletonCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}
