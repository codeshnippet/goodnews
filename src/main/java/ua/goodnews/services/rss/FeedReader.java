package ua.goodnews.services.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import ua.goodnews.dto.FeedEntry;

import java.util.List;

/**
 * Created by acidum on 2/25/17.
 */
public interface FeedReader {
    List<FeedEntry> read(String url);
}
