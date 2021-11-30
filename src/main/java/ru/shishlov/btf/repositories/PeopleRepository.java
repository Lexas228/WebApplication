package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.entities.PersonEntity;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByLogin(String login);
    boolean existsByLogin(String login);
    @Transactional
    void deleteByLogin(String login);
    @Modifying
    @Transactional
    @Query("UPDATE PersonEntity s SET s.password = :password where s.login = :login")
    void updatePasswordByLogin(@Param(value = "password") String password, @Param(value = "login") String login);
}
