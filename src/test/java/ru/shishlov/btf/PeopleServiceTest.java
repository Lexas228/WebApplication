package ru.shishlov.btf;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
import ru.shishlov.btf.dto.PersonInformationDto;
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

    @Value(value = "${images.path}")
    private String imagePath;

    @Value(value = "${common.image.name}")
    private String commonImageName;

    @MockBean
    private ImageRepositoryFS imageFS;

    @Autowired
    private ImageService imageService;

    @Test
    public void shouldBeInvalidToSave(){
       PersonDto personDto = new PersonDto();
       personDto.setLogin("Leo");
       personDto.setPassword("123");
       personDto.setConfirmPassword("321");
       assertFalse(peopleService.isAvailableToSave(personDto));
    }


    @Test
    public void shouldBeCorrectSaveAndDelete() throws ParseException {
        PersonDto p = createUser();
        peopleService.save(p);
        PersonDto per = peopleService.findByLogin(p.getLogin());
        assertNotNull(per);
        peopleService.delete(p.getLogin());
        PersonDto afterDelete = peopleService.findByLogin(p.getLogin());
        assertNull(afterDelete);
    }

    @Test
    public void shouldBeCorrectCreatingDirectoryForImageAndDeletingIt() throws ParseException {
        imageService.setRepository(imageFS);
        PersonDto p = createUser();
        peopleService.save(p);
        File fileItem = new File(imagePath +"/" + p.getLogin() + "/"+ commonImageName);
        assertTrue(Files.exists(fileItem.toPath()));
        peopleService.delete(p.getLogin());
        assertFalse(Files.exists(fileItem.toPath()));
    }

    @Test
    public void todo(){
        System.out.println(new Date());
    }

    public static PersonDto createUser() throws ParseException {
        PersonDto personDto = new PersonDto();
        PersonInformationDto personInformationDto = new PersonInformationDto();
        personDto.setLogin("leo");
        personDto.setPassword("param");
        personDto.setConfirmPassword("param");
        personInformationDto.setName("Aba");
        personInformationDto.setSurname("Bab");
        personInformationDto.setInformation("Blablabla");
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse("1984-11-24");
        personInformationDto.setBirthday(d);
        personInformationDto.setAddress("I don't know");
        ImageDto dto = new ImageDto();
        dto.setFile(new MockMultipartFile("lala", new byte[]{}));
        personInformationDto.setImage(dto);
        personDto.setPersonInformation(personInformationDto);
        return personDto;
    }




}
