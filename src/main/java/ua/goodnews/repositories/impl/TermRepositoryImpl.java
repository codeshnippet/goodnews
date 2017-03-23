package ua.goodnews.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.goodnews.model.Category;
import ua.goodnews.model.Term;
import ua.goodnews.repositories.TermRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by acidum on 1/28/17.
 */
public class TermRepositoryImpl implements TermRepositoryCustom {

    private static final int MINIMAL_TERM_OCCURRENCE_THRESHOLD = 1;

    @Autowired
    private EntityManager em;

    @Override
    public long getTermCountByCategoryAndThreshold(Category category) {
        Query query = em.createQuery("select coalesce(sum(t.count), 0) from Term t where t.text.category = :category and t.count > :threshold");
        query.setParameter("category", category);
        query.setParameter("threshold", MINIMAL_TERM_OCCURRENCE_THRESHOLD);
        Number result = (Number) query.getSingleResult();
        return result.longValue();
    }

    @Override
    public Term findTermByWordAndCategoryAndThreshold(String word, Category category) {
        Query query = em.createQuery("from Term t where t.word = :word and t.text.category = :category and t.count > :threshold");
        query.setParameter("word", word);
        query.setParameter("category", category);
        query.setParameter("threshold", MINIMAL_TERM_OCCURRENCE_THRESHOLD);
        List resultList = query.getResultList();
        if(resultList.isEmpty()){
            return null;
        } else{
            return (Term) resultList.get(0);
        }
    }

}
