package ru.shishlov.btf.components.convertors;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.MessageEntity;

@Component
public class MessageConvertor {

    public MessageDto toMessageDto(MessageEntity messageEntity, String from, String to){
        MessageDto messageDto = new MessageDto();
        messageDto.setDate(messageEntity.getDate());
        messageDto.setText(messageEntity.getText());
        messageDto.setLoginTo(messageEntity.getWhose().getLogin().equals(from) ? to : from);
        messageDto.setLoginFrom(messageEntity.getWhose().getLogin().equals(from) ? from : to);
        return messageDto;
    }

}
