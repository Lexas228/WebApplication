package ru.shishlov.btf.controllerTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.RequestPersonDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class DialogControllerTest extends AuthentincationProccessTest{

    protected RequestPersonDto ourFriend;

    protected Long dialogId;

    //we have already had one user from authentication, so we have to have one more, to create dialog with
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        //let's say it will be Leo1
        ourFriend = createUserWithName("Leo1");
    }

    @After
    public void clean() throws Exception {
        deleteUser("Leo1", "123");
    }

    @Test
    public void shouldCorrectCreateNewDialog() throws Exception {
        //we have already has Leo1 and we are authenticated as Leo
        String uri = "/dialog";
        DialogDto dialogDto = new DialogDto();
        dialogDto.setWithWhom("Leo1");
        String inputJson = mapToJson(dialogDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson).header(AUTHORIZATION, token)).andReturn();
        //we also have dialog output with id
        String outputJson = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        DialogDto responseDialog = mapFromJson(outputJson, DialogDto.class);
        assertNotNull(responseDialog.getId());
        //let's try to getAll dialogs for me and count it - expect 1
        MvcResult mvcc = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, token)).andReturn();

        String outputList = mvcc.getResponse().getContentAsString();
        System.out.println(outputList);
        DialogDto[] dtoList = mapFromJson(outputList, DialogDto[].class);
        assertEquals(1, dtoList.length);
        DialogDto dto = dtoList[0];
        assertEquals("Leo1", dto.getWithWhom());
        dialogId =dto.getId();
    }

    //only with first test
    @Test
    public void should403OnDeleteNotMyDialog() throws Exception {
        shouldCorrectCreateNewDialog();
        createUserWithName("Nano");
        String tok =  authenticate("Nano", "123");
        MvcResult mvcc = mvc.perform(MockMvcRequestBuilders.delete("/dialog/" + dialogId)
                .contentType(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, tok)).andReturn();

        int status = mvcc.getResponse().getStatus();
        assertEquals(403, status);
        deleteUser("Nano", "123");
    }

    //only with first test
    @Test
    public void shouldDeleteDialog() throws Exception {
        shouldCorrectCreateNewDialog();
        MvcResult mvcc = mvc.perform(MockMvcRequestBuilders.delete("/dialog/" + dialogId)
                .contentType(MediaType.APPLICATION_JSON_VALUE).header(AUTHORIZATION, token)).andReturn();
        int status = mvcc.getResponse().getStatus();
        assertEquals(200, status);
    }




}
