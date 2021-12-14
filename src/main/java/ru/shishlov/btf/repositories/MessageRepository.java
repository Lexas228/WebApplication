package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shishlov.btf.entities.MessageEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findMessageEntityByDialogIdOrderByDateDesc(Long dialogId);
}
