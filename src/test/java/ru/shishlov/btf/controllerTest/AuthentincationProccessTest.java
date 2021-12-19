package ru.shishlov.btf.controllerTest;


import org.junit.After;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shishlov.btf.dto.RequestPersonDto;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public abstract class AuthentincationProccessTest extends AbstractControllerTest{
    //let's create new user before every test here and go throw authentication process
    protected RequestPersonDto personDto;

    protected String token;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        personDto = createUserWithName("Leo");
        //now we have to authorize
        token = authenticate("Leo", "123");
        assertNotNull(token);
    }

    @After
    public void cleaning() throws Exception {
      deleteUser("Leo", "123");
    }

    protected void deleteUser(String login, String password) throws Exception {
        String tok = authenticate(login, password);
        String uri = "/people/" + login;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, tok)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    protected String authenticate(String login, String password) throws Exception {
        System.out.println(login + " " + password);
        MvcResult mvcc = mvc.perform(MockMvcRequestBuilders.post("/login")
                .param("login", login)
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcc.getResponse().getStatus();
        assertEquals(200, status);
        Map m = mapFromJson(mvcc.getResponse().getContentAsString(), Map.class);
        return (String) m.get("token");
    }
}
