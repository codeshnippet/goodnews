package ua.goodnews.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.goodnews.model.Feed;

/**
 * Created by acidum on 1/26/17.
 */
public interface RssFeedRepository extends CrudRepository<Feed, Long> {
}
