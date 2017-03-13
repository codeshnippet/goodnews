package ua.goodnews.controllers;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.CategoryRepository;
import ua.goodnews.repositories.FeedRepository;
import ua.goodnews.repositories.FilterRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by acidum on 2/26/17.
 */

@Controller
public class FiltersController {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FeedRepository feedRepository;

    @RequestMapping(value="/filters", method = RequestMethod.GET)
    public @ResponseBody
    List<Filter> getFilters() {
        List<Filter> result = new ArrayList<>();
        Iterator<Filter> iterator = filterRepository.findAll().iterator();
        while(iterator.hasNext()){
            Filter filter = iterator.next();
            filter.getFeeds().size();
            filter.getCategories().size();
            result.add(filter);
        }
        return result;
    }

    @RequestMapping(value="/filters", method = RequestMethod.POST)
    public @ResponseBody Filter create(@RequestBody Filter filter) throws BadHttpRequest {
        final Filter savedFilter = filterRepository.save(filter);
        filter.getCategories().stream().forEach(c -> c.setFilter(savedFilter));
        filter.getCategories().stream().forEach(categoryRepository::save);
        filter.getFeeds().stream().forEach(f -> f.setFilter(savedFilter));
        filter.getFeeds().stream().forEach(feedRepository::save);
        return savedFilter;
    }
}
