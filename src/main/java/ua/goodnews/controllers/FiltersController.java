package ua.goodnews.controllers;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.FilterRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acidum on 2/26/17.
 */

@Controller
public class FiltersController {

    @Autowired
    private FilterRepository filterRepository;

    @RequestMapping(value="/filters", method = RequestMethod.GET)
    public @ResponseBody
    List<Filter> getFilters() {
        List<Filter> result = new ArrayList<>();
        filterRepository.findAll().forEach(result::add);
        return result;
    }

    @RequestMapping(value="/filters", method = RequestMethod.POST)
    public @ResponseBody Filter create(@RequestBody Filter filter) throws BadHttpRequest {
        return filterRepository.save(filter);
    }
}
