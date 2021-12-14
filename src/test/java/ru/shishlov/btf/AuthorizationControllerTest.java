package ru.shishlov.btf;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shishlov.btf.controller.PeopleController;
import ru.shishlov.btf.dto.*;
import ru.shishlov.btf.services.PeopleService;

import java.text.ParseException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private PeopleController peopleController;

    @MockBean
    private PeopleService peopleService;

    private PersonDto personDto;

    @Test
    public void correctViewOfGetAuthorizationView() throws Exception {
        mockMvc.perform(get("/people/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/people/new"));

    }


    @Test
    public void failsWhenDifferentPasswordsGiven() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/people/new", personDto)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/people/new"));
    }

    @Test
    public void successCreatingUserWithJsonFile(){
        ObjectMapper objectMapper = new ObjectMapper();
        
    }



}
