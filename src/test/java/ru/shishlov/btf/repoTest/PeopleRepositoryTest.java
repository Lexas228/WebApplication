package ru.shishlov.btf.repoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.entities.PersonInformationEntity;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
public class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    private PersonEntity personEntity;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void createUser(){
        personEntity = new PersonEntity();
        personEntity.setLogin("Admin");
        personEntity.setPassword(passwordEncoder.encode("secret"));
        PersonInformationEntity personInformation = new PersonInformationEntity();
        personInformation.setInformation("I am admin");
        personInformation.setAddress("Rozok");
        personInformation.setBirthday(new Date());
        personInformation.setName("Admin");
        personInformation.setSurname("Adminov");
        personInformation.setLastAction(new Date());
        personInformation.setPerson(personEntity);
        personEntity.setPersonInformation(personInformation);
    }

    @After
    public void clean(){
        peopleRepository.deleteByLogin(personEntity.getLogin());
    }

    @Test
    public void shouldCorrectSave(){
        peopleRepository.save(personEntity);
        Optional<PersonEntity> pers = peopleRepository.findById(personEntity.getId());
        assertTrue(pers.isPresent());
    }
}
