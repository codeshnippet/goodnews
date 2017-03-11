package ua.goodnews.services.rss.impl;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import ua.goodnews.dto.FeedEntry;
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

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<FeedEntry> read(String url){
        List<SyndFeed> entries = getFeedEntries(url);

        List<FeedEntry> feedEntries = (List<FeedEntry>) conversionService.convert(entries,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(SyndEntry.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(FeedEntry.class)));

        return feedEntries;
    }

    private List<SyndFeed> getFeedEntries(String url) {
        try {
            URL feedSource = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            return feed.getEntries();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
