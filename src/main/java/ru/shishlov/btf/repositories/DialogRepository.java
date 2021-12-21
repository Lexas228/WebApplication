package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.DialogEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<DialogEntity, Long> {
    @Query("select d from DialogEntity d where :login in (select k.login from d.persons k)")
    List<DialogEntity> findAllByPersonLogin(@Param(value = "login") String login);

   @Query("select d from DialogEntity d where :loginOne in (select p.login from d.persons p) and :loginTwo in (select p.login from d.persons p) and (:loginOne<>:loginTwo or d.persons.size <= 1)")
   Optional<DialogEntity> findDialogByTwoLogin(String loginOne, String loginTwo);

   @Query("select d from DialogEntity d where d.id=:dialogId and exists (select p from d.persons p where p.login=:login)")
   Optional<DialogEntity> containsByDialogIdAndPersonLogin(Long dialogId, String login);



}
