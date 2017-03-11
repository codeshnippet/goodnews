package ua.goodnews.dto;

import ua.goodnews.model.Category;

import java.util.Date;

/**
 * Created by acidum on 1/20/17.
 */
public class FeedEntry {
    public String title;
    public String description;
    public Date publishedDate;
    public String link;
    public Category category;
}
