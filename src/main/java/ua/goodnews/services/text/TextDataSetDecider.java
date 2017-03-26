package ua.goodnews.services.text;

import ua.goodnews.model.Category;
import ua.goodnews.model.Text;

/**
 * Created by acidum on 3/26/17.
 */
public interface TextDataSetDecider {
    Text.DataSet calculateDataSet(String text, Category category);
}
