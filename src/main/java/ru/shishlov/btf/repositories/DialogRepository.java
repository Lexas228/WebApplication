package ru.shishlov.btf.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<DialogEntity, Long> {
    @Query("select d from DialogEntity d where exists (select p from d.personEntities p where p.login = :login)")
    List<DialogEntity> findAllByPersonLogin(@Param(value = "login") String login);

}
