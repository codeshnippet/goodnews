package ua.goodnews.converters;

import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.core.convert.converter.Converter;
import ua.goodnews.dto.FeedEntry;

/**
 * Created by acidum on 1/24/17.
 */
public class SyndEntryToNeewsEntryConverter implements Converter<SyndEntry, FeedEntry> {

    @Override
    public FeedEntry convert(SyndEntry syndEntry) {
        FeedEntry feedEntry = new FeedEntry();
        feedEntry.title = syndEntry.getTitle();
        feedEntry.description = syndEntry.getDescription().getValue();
        feedEntry.publishedDate = syndEntry.getPublishedDate();
        feedEntry.link = syndEntry.getLink();
        return feedEntry;
    }
}
