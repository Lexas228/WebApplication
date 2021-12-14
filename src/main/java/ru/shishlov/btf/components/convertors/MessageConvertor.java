package ru.shishlov.btf.components.convertors;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class MessageConvertor {
    private PeopleRepository peopleRepository;

    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public MessageDto toMessageDto(MessageEntity messageEntity){
        MessageDto messageDto = new MessageDto();
        messageDto.setText(messageEntity.getText());
        messageDto.setLoginFrom(messageEntity.getWhose().getLogin());
        return messageDto;
    }

    public MessageEntity toMessageEntity(MessageDto messageDto){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setWhose(peopleRepository.findByLogin(messageDto.getLoginFrom()).orElse(null));
        messageEntity.setDate(new Timestamp(new Date().getTime()));
        messageEntity.setText(messageDto.getText());
        return messageEntity;
    }

}
