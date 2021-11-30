package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shishlov.btf.entities.MessageEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    @Query("select m from MessageEntity m where m.dialog.id = :id order by m.date desc")
    List<MessageEntity> findByDialogId(Long id);
}
