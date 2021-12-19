package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class MessageConvertor {

    public MessageDto toMessageDto(MessageEntity messageEntity){
        MessageDto messageDto = new MessageDto();
        messageDto.setText(messageEntity.getText());
        messageDto.setLoginFrom(messageEntity.getWhose().getLogin());
        messageDto.setDate(messageEntity.getDate());
        messageDto.setDialogId(messageEntity.getDialog().getId());
        return messageDto;
    }


}
