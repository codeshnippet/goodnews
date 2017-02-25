package ua.goodnews.model;

import javax.persistence.*;

/**
 * Created by acidum on 1/26/17.
 */

@Entity
public class Term {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Text text;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private int count = 1;

    protected Term(){}

    public Term(Text text, String word) {
        this.text = text;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        setCount(getCount() + 1);
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
