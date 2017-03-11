package ua.goodnews.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.goodnews.repositories.FeedRepository;

import static org.junit.Assert.*;

/**
 * Created by acidum on 1/26/17.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedTest {

    @Autowired
    private FeedRepository repo;

    @Test
    public void testNewRssFeed(){
        Feed feed = new Feed();
        feed.setUrl("http://findWordByWordAndPositive.com/rss");
        repo.save(feed);

        Feed foundFeed = repo.findAll().iterator().next();
        assertEquals("http://findWordByWordAndPositive.com/rss", foundFeed.getUrl());
    }

}