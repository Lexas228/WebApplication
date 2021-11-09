package ru.shishlov.btf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.services.PeopleService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.application.properties")
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;


    @Test
    public void invalidCheckToSave(){
       PersonDto personDto = new PersonDto();
       personDto.setLogin("Leo");
       personDto.setPassword("123");
       personDto.setConfirmPassword("321");
       assertFalse(peopleService.isAvailableToSave(personDto));
    }

    @Test
    public void delete(){
        peopleService.delete("leo");
        Collection<PersonDto> list = peopleService.getAll();
        assertEquals(1, list.size());
    }

    @Test
    public void validCheckToSave(){
        PersonDto p = createUser();
        peopleService.save(p);
        PersonDto per = peopleService.findByLogin(p.getLogin());
        assertNotNull(per);
    }

    private PersonDto createUser(){
        PersonDto personDto = new PersonDto();
        PersonInformationDto personInformationDto = new PersonInformationDto();
        personDto.setLogin("leo");
        personDto.setPassword("param");
        personDto.setConfirmPassword("param");
        personInformationDto.setName("Aba");
        personInformationDto.setSurname("Bab");
        personInformationDto.setInformation("Blablabla");
        personInformationDto.setBirthday(new Date());
        personInformationDto.setAddress("I don't know");
        personDto.setPersonInformation(personInformationDto);
        return personDto;
    }




}
