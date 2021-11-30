package ru.shishlov.btf.repositories;

import liquibase.pro.packaged.Q;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.entities.PersonInformationEntity;

import java.util.Date;

@Repository
public interface PeopleInformationRepository extends JpaRepository<PersonInformationEntity, Long> {

    @Query("select p from PersonInformationEntity p where p.person.login = :login")
    PersonInformationEntity findByPersonLogin(@Param(value="login") String login);

    @Modifying
    @Transactional
    @Query("update PersonInformationEntity p set p.lastAction = :date where p.person.login = :login")
    void updateLastAction(@Param(value = "date") java.sql.Date date, @Param(value = "login") String login);

}
