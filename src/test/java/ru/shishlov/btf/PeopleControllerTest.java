package ru.shishlov.btf;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shishlov.btf.components.PersonParser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonParser personParser;

    public void shouldAddNewUser(){

    }



}
