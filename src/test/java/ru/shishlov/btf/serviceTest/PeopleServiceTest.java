package ru.shishlov.btf.serviceTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.repositories.ImageRepositoryFS;
import ru.shishlov.btf.services.ImageService;
import ru.shishlov.btf.services.PeopleService;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
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
        PersonDto per = new PersonDto();
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
        PersonDto per = new PersonDto();
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
