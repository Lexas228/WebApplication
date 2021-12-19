package ru.shishlov.btf.serviceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.dto.ResponsePersonDto;
import ru.shishlov.btf.services.DialogService;
import org.junit.Assert;
import ru.shishlov.btf.services.PeopleService;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DialogServiceTest {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private PeopleService peopleService;

    private final String username = "Leo";

    @Before
    public void setUp(){
        for(int i = 0; i < 10; i++){
            RequestPersonDto per = new RequestPersonDto();
            per.setLogin(username+ i);
            per.setPassword("123");
            per.setInformation("lala");
            per.setName("dima");
            per.setSurname("shishlov");
            per.setBirthday(new Date());
            per.setAddress("kaka");
            peopleService.save(per);
            Assert.assertNotNull(peopleService.findByLogin("Leo"+i));
        }
    }

    @After
    public void cleaning(){
        for(int i = 0; i < 10; i++){
            peopleService.delete(username + i);
        }
    }



    //if we have already had users (and we have it).
    @Test
    public void shouldCreateDialog(){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setWithWhom("Leo0");
        //second parameter - current user login, we need it for dto convert(it has field "withWhom")
        dialogDto = dialogService.create(dialogDto, "Leo1");
        assertNotNull(dialogDto.getId());
        DialogDto dig = dialogService.getDialogById(dialogDto.getId(), "Leo0");
        assertNotNull(dig);
        //expect that our dialog will be with Leo1, because get it for Leo0
        assertEquals("Leo1", dig.getWithWhom());
        //now let's do it from other side and get it from Leo1, expect it will be with Leo0

        DialogDto d = dialogService.getDialogById(dialogDto.getId(), "Leo1");
        assertEquals("Leo0", d.getWithWhom());
    }

    @Test
    public void shouldGetAll(){
        //lets try to create dialogs for Leo with each user and then get it
        //we have to get all users
        List<ResponsePersonDto> personDtos = peopleService.getAll().stream().toList();
        //ok, now lets create dialogs
        //active user is Leo0
        String activeUserName = "Leo0";
        personDtos.forEach(personDto -> {
            DialogDto dialogDto = new DialogDto();
            dialogDto.setWithWhom(personDto.getLogin());
            //now save it
            dialogService.create(dialogDto, activeUserName);
        });
        //ok, now lets try to get it
        List<DialogDto> dtoList = dialogService.findAllDialogsFor(activeUserName);
        //sizes of list persons and list of dialogs must be equal
        assertEquals(dtoList.size(), personDtos.size());
        //now let's clean it and check what we have after cleaning
        dtoList.forEach(dialogDto -> {
            dialogService.delete(dialogDto);
        });
        //let's try to get all dialogs again
        List<DialogDto> list = dialogService.findAllDialogsFor(activeUserName);
        //expect empty
        assertTrue(list.isEmpty());
    }
}
