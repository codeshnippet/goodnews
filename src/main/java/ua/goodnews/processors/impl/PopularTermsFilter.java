package ua.goodnews.processors.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.goodnews.processors.TextProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by acidum on 2/19/17.
 */

@Component
public class PopularTermsFilter implements TextProcessor {

    private static final String MOST_POPULAR_EN_PATH = "src/main/resources/most-popular-words-en.txt";

    private Set<String> mostPopular;

    public PopularTermsFilter() throws IOException {
        mostPopular = new HashSet<>();
        Path path = Paths.get(MOST_POPULAR_EN_PATH);
        Stream<String> stream = Files.lines(path);
        stream.forEach(mostPopular::add);
    }

    @Override
    public String process(String text) {
        StringBuilder builder = new StringBuilder();
        for(String word: text.split(" ")) {
            if(mostPopular.contains(word)){
                continue;
            }
            builder.append(word + " ");
        }

        return builder.toString();
    }
}
