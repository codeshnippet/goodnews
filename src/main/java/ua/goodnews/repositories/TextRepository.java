package ua.goodnews.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.goodnews.model.Category;
import ua.goodnews.model.Term;
import ua.goodnews.model.Text;

/**
 * Created by acidum on 1/27/17.
 */
public interface TextRepository extends CrudRepository<Text, Long> {
    Long countByCategory(Category category);
    long countByDataSet(Text.DataSet dataSet);
}
