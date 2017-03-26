package ua.goodnews.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.goodnews.dto.FeedEntry;
import ua.goodnews.model.Category;
import ua.goodnews.model.Feed;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.CategoryRepository;
import ua.goodnews.repositories.FeedRepository;
import ua.goodnews.repositories.FilterRepository;
import ua.goodnews.services.bayes.BayesClassifier;
import ua.goodnews.services.rss.FeedReader;
import ua.goodnews.services.terms.TermAccumulator;
import ua.goodnews.services.text.TextService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedsController.class)
public class FeedsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BayesClassifier bayesClassifier;

    @MockBean
    private FeedReader feedReader;

    @MockBean
    private FilterRepository filterRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private TextService textService;

    @Test
    public void testGetFeed() throws Exception {
        // Given
        Feed feed = new Feed();
        feed.setUrl("http://feed.com");
        feed.setId(12l);

        Category good = new Category("good");
        Category bad = new Category("bad");

        List<Category> categories = Arrays.asList(good, bad);

        Filter goodBad = new Filter();
        goodBad.setCategories(categories);
        goodBad.setFeeds(Arrays.asList(feed));

        feed.setFilter(goodBad);

        FeedEntry feedEntryOne = new FeedEntry();
        feedEntryOne.description = "NEWS-TEXT";
        feedEntryOne.category = good;

        FeedEntry feedEntryTwo = new FeedEntry();
        feedEntryTwo.description = "OTHER-TEXT";
        feedEntryTwo.category = bad;

        given(this.filterRepository.findOne(31l)).willReturn(goodBad);
        given(this.feedReader.read("http://feed.com")).willReturn(Arrays.asList(feedEntryOne, feedEntryTwo));

        when(bayesClassifier.classify(eq("NEWS-TEXT"), eq(categories))).thenReturn(good);

        when(bayesClassifier.classify(eq("OTHER-TEXT"), eq(categories))).thenReturn(bad);

        // When
        this.mvc.perform(get("/filters/31/feeds/12").accept(MediaType.APPLICATION_JSON))

        // Then
        .andExpect(status().isOk())
        .andExpect(content().json("{\"url\":\"http://feed.com\"," +
                "\"entries\":[" +
                "{\"description\":\"NEWS-TEXT\",\"category\":{\"name\":\"good\"}}," +
                "{\"description\":\"OTHER-TEXT\",\"category\":{\"name\":\"bad\"}}]}"));
    }
}
