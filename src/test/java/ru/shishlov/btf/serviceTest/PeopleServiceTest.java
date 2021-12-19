package ru.shishlov.btf.serviceTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.services.PeopleService;

import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;

    @Test
    public void shouldBeValidLogin(){
        peopleService.delete("leo");
        Assert.assertTrue(peopleService.isAvailableLogin("leo"));
    }

    @Test
    public void shouldBeInvalidLogin(){
        RequestPersonDto per = new RequestPersonDto();
        per.setLogin("Leo");
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("dima");
        per.setSurname("shishlov");
        per.setBirthday(new Date());
        per.setAddress("kaka");
        peopleService.save(per);
        Assert.assertFalse(peopleService.isAvailableLogin("Leo"));
        peopleService.delete("Leo");
        assertNull(peopleService.findByLogin("Leo"));
    }

    @Test
    public void shouldBeValidAddingUser(){
        RequestPersonDto per = new RequestPersonDto();
        per.setLogin("Leo");
        per.setPassword("123");
        per.setInformation("lala");
        per.setName("dima");
        per.setSurname("shishlov");
        per.setBirthday(new Date());
        per.setAddress("kaka");
        peopleService.save(per);
        Assert.assertNotNull(peopleService.findByLogin("Leo"));
    }
}
