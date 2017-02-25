package ua.goodnews.services.rss.impl;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Service;
import ua.goodnews.services.rss.FeedReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by acidum on 1/20/17.
 */

@Service
public class FeedReaderImpl implements FeedReader {

    @Override
    public List<SyndEntry> read(String url){
        URL feedSource = null;
        try {
            feedSource = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed feed = input.build(new XmlReader(feedSource));
            return feed.getEntries();
        } catch (FeedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
