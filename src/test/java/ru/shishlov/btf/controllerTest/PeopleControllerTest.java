package ru.shishlov.btf.controllerTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.repositories.PeopleRepository;
import ru.shishlov.btf.services.PeopleService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PeopleControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleServiceMock;
    //integration test

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    public void shouldBeCorrectAddingNewUser() throws Exception {
        String uri = "/people";
        RequestPersonDto per = new RequestPersonDto();
        per.setLogin("Leo");
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("dima");
        per.setSurname("shishlov");
        per.setBirthday(new Date());
        per.setAddress("kaka");
        when(peopleServiceMock.isAvailableLogin(any())).thenReturn(true);

        String inputJson = mapToJson(per);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        assertEquals(200, status);
        assertEquals(content, "User was created");
        verify(peopleServiceMock, times(0)).save(per);
    }
}
