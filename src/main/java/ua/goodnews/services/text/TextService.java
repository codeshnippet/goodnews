package ua.goodnews.services.text;

import ua.goodnews.model.Category;

/**
 * Created by acidum on 3/26/17.
 */
public interface TextService {
    void saveText(String content, Category category);
}
