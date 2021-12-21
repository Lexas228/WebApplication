package ru.shishlov.btf.controllerTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.shishlov.btf.dto.RequestPersonDto;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected RequestPersonDto createUserWithName(String name) throws Exception {
        String uri = "/people";
        RequestPersonDto per = new RequestPersonDto();
        per.setLogin(name);
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("dima");
        per.setSurname("shishlov");
        per.setBirthday(new Date());
        per.setAddress("kaka");

        String inputJson = mapToJson(per);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals(content, "User was created");
        return per;
    }

    protected void deleteUser(String login){

    }
}
