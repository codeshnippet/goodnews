package ua.goodnews.model;

import javax.persistence.*;

/**
 * Created by acidum on 2/18/17.
 */

@Entity
public class Text {

    public enum DataSet {
        LEARNING, TEST
    }

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String content;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private DataSet dataSet = DataSet.LEARNING;

    public Text(){}

    public Text(String content, Category category) {
        this.content = content;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
