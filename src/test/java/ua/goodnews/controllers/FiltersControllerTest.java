package ua.goodnews.controllers;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.goodnews.model.Category;
import ua.goodnews.model.Feed;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.CategoryRepository;
import ua.goodnews.repositories.FeedRepository;
import ua.goodnews.repositories.FilterRepository;
import ua.goodnews.utils.JsonTestUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by acidum on 2/26/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(FiltersController.class)
public class FiltersControllerTest extends TestCase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilterRepository filterRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private FeedRepository feedRepository;

    @Test
    public void testGetAllFilters() throws Exception {
        Feed feed = new Feed();
        feed.setId(3l);
        feed.setUrl("http://feed.com");

        Filter filter = new Filter("test-filter");
        filter.setId(1l);
        filter.setFeeds(Arrays.asList(feed));
        filter.setCategories(Arrays.asList(new Category("good"), new Category("bad")));

        given(this.filterRepository.findAll()).willReturn(Arrays.asList(filter));

        this.mvc.perform(get("/filters").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(
                "[{\"id\":1," +
                        "\"name\":\"test-filter\"," +
                        "\"feeds\":[{\"id\":3,\"url\":\"http://feed.com\"}]," +
                        "\"categories\":[{\"name\":\"good\"},{\"name\":\"bad\"}]," +
                        "}]"
        ));
    }

    @Test
    public void testCreate() throws Exception {
        Feed feed = new Feed();
        feed.setUrl("http://feed.com");

        Filter newFilter = new Filter("new-test-filter");
        newFilter.setFeeds(Arrays.asList(feed));
        newFilter.setCategories(Arrays.asList(new Category("good"), new Category("bad")));
        given(this.filterRepository.save(any(Filter.class))).willAnswer(invocation -> {
            newFilter.setId(1l);
            return newFilter;
        });

        this.mvc.perform(post("/filters")
                .content(JsonTestUtil.convertObjectToJsonBytes(newFilter))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(
                "{\"id\":1," +
                        "\"name\":\"new-test-filter\"," +
                        "\"feeds\":[{\"url\":\"http://feed.com\"}]," +
                        "\"categories\":[{\"name\":\"good\"},{\"name\":\"bad\"}]," +
                        "}"
        ));
    }

}