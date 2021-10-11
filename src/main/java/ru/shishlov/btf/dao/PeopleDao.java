package ru.shishlov.btf.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
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

    public Collection<Person> getAll(){
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    public long add(Person person){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {PreparedStatement ps  = con.prepareStatement("INSERT INTO people(login, password) VALUES (?, ?)", new String[]{"id"});
            ps.setString(1, person.getLogin());ps.setString(2, person.getPassword()); return ps;}, kh);
        return (long)kh.getKey();
    }

    public void updatePassword(String login, String newPassword){
        jdbcTemplate.update("UPDATE people SET password=? where login = ?", newPassword, login);
    }


    public long delete(String login){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {PreparedStatement ps  = con.prepareStatement("DELETE from people where login=?", new String[]{"id"});
            ps.setString(1, login); return ps;}, kh);
        return (long)kh.getKey();
    }
}
