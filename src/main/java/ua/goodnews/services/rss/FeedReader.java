package ua.goodnews.services.rss;

import com.sun.syndication.feed.synd.SyndEntry;

import java.util.List;

/**
 * Created by acidum on 2/25/17.
 */
public interface FeedReader {
    List<SyndEntry> read(String url);
}
