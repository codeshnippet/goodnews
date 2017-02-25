package ua.goodnews.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.goodnews.model.Category;
import ua.goodnews.model.Term;

/**
 * Created by acidum on 1/27/17.
 */
public interface TermRepository extends CrudRepository<Term, Long>, TermRepositoryCustom {

    Term findTermByWordAndTextCategory(String word, Category category);
}
