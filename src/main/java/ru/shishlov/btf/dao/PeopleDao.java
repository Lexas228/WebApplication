package ru.shishlov.btf.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.model.Person;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDao {
    //will do something with data base

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PeopleDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person findByLogin(final String login){
        return jdbcTemplate.query("SELECT * FROM PEOPLE WHERE login=?",new Object[]{login},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public List<Person> getAll(){
        return jdbcTemplate.query("SELECT * FROM PEOPLE", new BeanPropertyRowMapper<>(Person.class));
    }

    public void add(Person person){
        jdbcTemplate.update("INSERT INTO People VALUES (?, ?, ?, ?, ?, ?, ?)",
                person.getName(), person.getSurname(), person.getLogin(), person.getPassword()
                , person.getInformation(), new java.util.Date(person.getBirthday().getTime()), person.getAddress());
    }

    public void update(String login, Person newPerson) {
        jdbcTemplate.update("UPDATE People SET name=?, surname=?, password=?, information=?, birthday=?, address=?" +
                "where login=?", newPerson.getName(), newPerson.getSurname(), newPerson.getPassword(), newPerson.getInformation(),new java.util.Date(newPerson.getBirthday().getTime())
        , newPerson.getAddress(), login);
    }


    public void delete(String login){
        jdbcTemplate.update("DELETE from People where login=?", login);
    }
}
