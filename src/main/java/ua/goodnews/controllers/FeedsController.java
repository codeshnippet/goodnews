package ua.goodnews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.goodnews.dto.FeedEntry;
import ua.goodnews.model.Category;
import ua.goodnews.model.Feed;
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

    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public void teach(String text, Category category){
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
