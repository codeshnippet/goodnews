package ua.goodnews.services.bayes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.goodnews.model.Category;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.CategoryRepository;
import ua.goodnews.repositories.FilterRepository;
import ua.goodnews.repositories.TermRepository;
import ua.goodnews.repositories.TextRepository;
import ua.goodnews.services.terms.TermAccumulator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by acidum on 2/25/17.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class BayesClassifierTest {

    private static Logger logger = Logger.getLogger(BayesClassifierTest.class.getName());

    @Autowired
    private BayesClassifier bayesClassifier;

    @Autowired
    private TermAccumulator termAccumulator;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TextRepository textRepository;

    private Filter petFilter;
    private Category dogCategory;
    private Category catCategory;
    private String[] goodTestTexts;
    private String[] badTestTexts;

    public BayesClassifierTest() throws IOException {
        goodTestTexts = readTextsFromFolder("src/test/resources/good-test", 1000);
        badTestTexts = readTextsFromFolder("src/test/resources/bad-test", 1000);
    }

    @Test
    public void testClassificationResultsAreGrowing() throws IOException {
        int dataSetSize = 15;
        double[] results = new double[dataSetSize];

        for (int i = 0; i < dataSetSize; i ++) {
            initDbObjects();
            teachSystem(i + 1);
            results[i] =  getClassificationSuccessRate();
            clearDB();

            logger.info("Classification accuracy for dataset of size " + i + " is " + results[i]);
            if(i > 0){
                assertTrue(results[i] >= results[i - 1]);
            }
        }

        assertTrue(results[dataSetSize - 1] > results[0]);
    }

    public void clearDB(){
        termRepository.deleteAll();
        textRepository.deleteAll();
        categoryRepository.deleteAll();
        filterRepository.deleteAll();
    }

    private double getClassificationSuccessRate() throws IOException {
        double goodCount = 0;
        //Test on good test texts

        for (String text: goodTestTexts){
            Category result = bayesClassifier.classify(text, petFilter.getCategories());
            if("dog".equals(result.getName())) {
                goodCount += 1;
            }
        }

        //Test on bad test texts
        for (String text: badTestTexts){
            Category result = bayesClassifier.classify(text, petFilter.getCategories());
            if("cat".equals(result.getName())) {
                goodCount += 1;
            }
        }

        return goodCount / 6.0;
    }

    private void teachSystem(int maxFiles) throws IOException {
        //Teach good words
        String[] goodTexts = readTextsFromFolder("src/test/resources/good", maxFiles);
        for (String text: goodTexts){
            termAccumulator.accumulate(text, dogCategory);
        }

        //Teach bad words
        String[] badTexts = readTextsFromFolder("src/test/resources/bad", maxFiles);
        for (String text: badTexts){
            termAccumulator.accumulate(text, catCategory);
        }
    }

    private void initDbObjects() {
        petFilter = new Filter("dog-filter");
        filterRepository.save(petFilter);

        dogCategory = new Category("dog", petFilter);
        categoryRepository.save(dogCategory);

        catCategory = new Category("cat", petFilter);
        categoryRepository.save(catCategory);

        petFilter.setCategories(Arrays.asList(catCategory, dogCategory));
        filterRepository.save(petFilter);
    }

    private String[] readTextsFromFolder(String folderPath, int maxFiles) throws IOException {

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        int numberOfFiles = maxFiles < listOfFiles.length ? maxFiles : listOfFiles.length;
        String[] result = new String[numberOfFiles];


        for (int i = 0; i < numberOfFiles; i++) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                Path path = FileSystems.getDefault().getPath(file.getPath());
                String s = Files.lines(path, StandardCharsets.UTF_8).map(e -> e.toString()).reduce("", String::concat);
                result[i] = s;
            }
        }

        return result;
    }

}