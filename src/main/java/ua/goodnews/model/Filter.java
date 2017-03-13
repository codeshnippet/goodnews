package ua.goodnews.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by acidum on 1/27/17.
 */
@Entity
public class Filter {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "filter")
    private List<Category> categories;

    @OneToMany(mappedBy = "filter")
    private List<Feed> feeds;

    public Filter(){}

    public Filter(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }
}
