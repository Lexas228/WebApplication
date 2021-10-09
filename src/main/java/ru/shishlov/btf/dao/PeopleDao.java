package ru.shishlov.btf.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;

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
        return jdbcTemplate.query("SELECT * FROM people WHERE login=?",new Object[]{login},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }




    public void add(Person person){
        jdbcTemplate.update("INSERT INTO people VALUES (?, ?)",
                person.getLogin(), person.getPassword());

    }

    public void updatePassword(String login, String newPassword){
        jdbcTemplate.update("UPDATE people SET password=? where login = ?", newPassword, login);
    }


    public void delete(String login){
        jdbcTemplate.update("DELETE from people where login=?", login);
    }
}
