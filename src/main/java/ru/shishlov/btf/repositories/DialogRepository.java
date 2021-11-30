package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.DialogEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<DialogEntity, Long> {

    @Query("select d from DialogEntity d where d.firstPerson.login = :login or d.secondPerson.login = :login")
    List<DialogEntity> findAllByPersonLogin(String login);

    @Query("select d from DialogEntity d where (d.firstPerson.login = :loginOne and d.secondPerson.login = :loginTwo)" +
            "or (d.firstPerson.login = :loginTwo and d.secondPerson.login = :loginOne)")
    Optional<DialogEntity> findDialogBetweenTwoPerson(@Param("loginOne") String loginOne, @Param("loginTwo") String loginTwo);


}
