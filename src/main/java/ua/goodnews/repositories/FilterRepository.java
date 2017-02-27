package ua.goodnews.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.goodnews.model.Filter;

/**
 * Created by acidum on 1/27/17.
 */
public interface FilterRepository extends CrudRepository<Filter, Long> {
}
