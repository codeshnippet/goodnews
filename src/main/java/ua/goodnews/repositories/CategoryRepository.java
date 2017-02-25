package ua.goodnews.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.goodnews.model.Category;

/**
 * Created by acidum on 2/9/17.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
