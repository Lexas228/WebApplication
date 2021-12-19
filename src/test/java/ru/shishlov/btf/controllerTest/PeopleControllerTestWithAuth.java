package ru.shishlov.btf.controllerTest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.dto.ResponsePersonDto;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class PeopleControllerTestWithAuth extends AuthentincationProccessTest{


    @Test
    public void shouldBeCorrectGettingUser() throws Exception {
        String uri = "/people/Leo";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ResponsePersonDto person = super.mapFromJson(content, ResponsePersonDto.class);
        assertNotNull(person);
        assertEquals("Leo", person.getLogin());
    }

    @Test
    public void shouldBe403OnDeletingNotMyAccount() throws Exception {
        //my account is Leo
        //will try to delete Leo1
        String uri = "/people/Leo1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, token)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);
    }

    @Test
    public void shouldCorrectPut() throws Exception {
        String uri = "/people/Leo";

        RequestPersonDto per = new RequestPersonDto();
        per.setLogin("Leo");
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("I am not Dima");
        per.setSurname("I am");
        per.setBirthday(new Date());
        per.setAddress("kaka");
        String inputJson = mapToJson(per);

        //we have to get access before

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson).header(AUTHORIZATION, token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        //let's get it and look at the result

        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals(200, status2);
        String content = mvcResult2.getResponse().getContentAsString();
        ResponsePersonDto person = super.mapFromJson(content, ResponsePersonDto.class);
        assertEquals("I am not Dima", person.getName());
    }


}
