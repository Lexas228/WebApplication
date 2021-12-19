package ru.shishlov.btf.serviceTest;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.services.DialogService;
import ru.shishlov.btf.services.MessageService;
import ru.shishlov.btf.services.PeopleService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private DialogService dialogService;

    private Long dialogId;

    @Before
    public void setUp(){
        //let's create a pair of users and one dialog where we will chat in
        RequestPersonDto per = new RequestPersonDto();
        per.setLogin("Dima");
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("dima");
        per.setSurname("shishlov");
        per.setBirthday(new Date());
        per.setAddress("kaka");
        peopleService.save(per);
        Assert.assertNotNull(peopleService.findByLogin("Dima"));

        RequestPersonDto per1 = new RequestPersonDto();
        per1.setLogin("Alex");
        per1.setPassword("123");
        per1.setInformation("lala");
        per1.setName("alex");
        per1.setSurname("shishlov");
        per1.setBirthday(new Date());
        per1.setAddress("kaka");
        peopleService.save(per1);
        Assert.assertNotNull(peopleService.findByLogin("Alex"));

        //now creating chat
        DialogDto dialogDto = new DialogDto();
        dialogDto.setWithWhom("Alex");
        dialogDto = dialogService.create(dialogDto, "Dima");
        Assert.assertNotNull(dialogService.getDialogById(dialogDto.getId(), "Alex"));
        dialogId = dialogDto.getId();
    }

    @After
    public void cleaning(){
        peopleService.delete("Dima");
        peopleService.delete("Alex");
    }

    @Test
    public void shouldAddNewMessage(){
        //now will try to send message and get it from other user
        //we will not test websocket now, so we have to get from base
        MessageDto messageDto = new MessageDto();
        messageDto.setDialogId(dialogId);
        messageDto.setLoginFrom("Dima");
        messageDto.setText("Hello everyBody");
        messageService.add(messageDto);

        //now try to get it throw the dialog

        List<MessageDto> messageDtoList = messageService.getAllMessagesByDialogId(dialogId).stream().toList();
        assertFalse(messageDtoList.isEmpty());

        assertEquals("Hello everyBody", messageDtoList.get(0).getText());
    }




}
