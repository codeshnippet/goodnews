package ua.goodnews.converters;

import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.core.convert.converter.Converter;
import ua.goodnews.dto.NewsEntry;

/**
 * Created by acidum on 1/24/17.
 */
public class SyndEntryToNeewsEntryConverter implements Converter<SyndEntry, NewsEntry> {

    @Override
    public NewsEntry convert(SyndEntry syndEntry) {
        NewsEntry newsEntry = new NewsEntry();
        newsEntry.title = syndEntry.getTitle();
        newsEntry.description = syndEntry.getDescription().getValue();
        newsEntry.publishedDate = syndEntry.getPublishedDate();
        newsEntry.link = syndEntry.getLink();
        return newsEntry;
    }
}
