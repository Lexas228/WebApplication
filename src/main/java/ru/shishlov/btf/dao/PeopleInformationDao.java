package ru.shishlov.btf.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;

import java.util.Collection;
import java.util.List;

@Component
public class PeopleInformationDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PeopleInformationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<PersonInformation> getAll(){
        return jdbcTemplate.query("SELECT * FROM people_information", new BeanPropertyRowMapper<>(PersonInformation.class));
    }

    public PersonInformation findByLogin(final String login){
        return jdbcTemplate.query("SELECT * FROM people_information WHERE login=?",new Object[]{login},
                new BeanPropertyRowMapper<>(PersonInformation.class)).stream().findAny().orElse(null);
    }

    public void add(PersonInformation personInformation){
        jdbcTemplate.update("INSERT INTO people_information VALUES(?, ?, ?, ?, ?, ?)",
                personInformation.getLogin(), personInformation.getName(), personInformation.getSurname(),
                personInformation.getBirthday(), personInformation.getAddress(), personInformation.getInformation());

    }

    public void update(String login, PersonInformation personInformation) {
        jdbcTemplate.update("UPDATE people_information SET name=?, surname=?, information=?, birthday=?, address=?" +
                        "where login=?", personInformation.getName(), personInformation.getSurname(), personInformation.getInformation(),personInformation.getBirthday(),
                personInformation.getAddress(), login);
    }

    public void delete(String login){
        jdbcTemplate.update("DELETE from people_information where login=?", login);
    }




}
