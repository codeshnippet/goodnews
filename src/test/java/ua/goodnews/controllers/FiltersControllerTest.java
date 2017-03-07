package ua.goodnews.controllers;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.goodnews.model.Filter;
import ua.goodnews.repositories.FilterRepository;
import ua.goodnews.utils.JsonTestUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by acidum on 2/26/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(FiltersController.class)
public class FiltersControllerTest extends TestCase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilterRepository filterRepository;

    @Test
    public void testGetAllFilters() throws Exception {
        List<Filter> list = Arrays.asList(new Filter("test-filter"));
        given(this.filterRepository.findAll()).willReturn(list);

        this.mvc.perform(get("/filters").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("[{\"name\":\"test-filter\"}]"));
    }

    @Test
    public void testCreate() throws Exception {
        Filter newFilter = new Filter("new-test-filter");
        given(this.filterRepository.save(any(Filter.class))).willAnswer(invocation -> {
            newFilter.setId(1l);
            return newFilter;
        });

        this.mvc.perform(post("/filters")
                .content(JsonTestUtil.convertObjectToJsonBytes(newFilter))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json("{\"id\":1, \"name\":\"new-test-filter\"}"));
    }

}