package ua.goodnews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.goodnews.dto.FeedEntry;

import javax.persistence.*;
import java.util.List;

/**
 * Created by acidum on 1/26/17.
 */

@Entity
public class Feed {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JsonIgnore
    private Filter filter;

    @Transient
    private List<FeedEntry> entries;

    public Feed(){

    }

    public Feed(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public List<FeedEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FeedEntry> entries) {
        this.entries = entries;
    }
}
