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

    public PersonInformation findByPersonId(final long id){
        return jdbcTemplate.query("SELECT * FROM people_information WHERE person_id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(PersonInformation.class)).stream().findAny().orElse(null);
    }

    public void add(PersonInformation personInformation){
        jdbcTemplate.update("INSERT INTO people_information(person_id, name, surname, birthday, address, information) VALUES(?, ?, ?, ?, ?, ?)",
                personInformation.getPersonId(), personInformation.getName(), personInformation.getSurname(),
                personInformation.getBirthday(), personInformation.getAddress(), personInformation.getInformation());

    }

    public void update(long personId, PersonInformation personInformation) {
        jdbcTemplate.update("UPDATE people_information SET name=?, surname=?, information=?, birthday=?, address=?" +
                        "where person_id=?", personInformation.getName(), personInformation.getSurname(), personInformation.getInformation(),personInformation.getBirthday(),
                personInformation.getAddress(), personId);
    }

    public void delete(long personId){
        jdbcTemplate.update("DELETE from people_information where person_id=?", personId);
    }




}
