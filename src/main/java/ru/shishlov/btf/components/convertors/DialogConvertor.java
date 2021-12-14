package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DialogConvertor {
    private MessageConvertor messageConvertor;
    private PeopleRepository peopleRepository;

    @Autowired
    public void setMessageConvertor(MessageConvertor messageConvertor) {
        this.messageConvertor = messageConvertor;
    }

    @Autowired
    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public DialogDto toDialogDto(DialogEntity dialogEntity){
        DialogDto dialogDto = new DialogDto();

        List<String> personLoginsList = dialogEntity.getPersonEntities().stream()
                .map(PersonEntity::getLogin).collect(Collectors.toList());
        List<MessageDto> messageDtoList = dialogEntity.getMessageEntities().stream()
                .map(messageEntity -> messageConvertor.toMessageDto(messageEntity))
                .collect(Collectors.toList());
        dialogDto.setMessages(messageDtoList);
        dialogDto.setPeopleLogins(personLoginsList);
        dialogDto.setId(dialogDto.getId());
         return dialogDto;
    }

    public DialogEntity toDialogEntity(DialogDto dialogDto){
        DialogEntity dialogEntity = new DialogEntity();
        List<PersonEntity> personEntities = dialogDto.getPeopleLogins().stream()
                .map(login -> peopleRepository.findByLogin(login).orElse(null))
                .collect(Collectors.toList());

        List<MessageEntity> messageEntities = dialogDto.getMessages().stream()
                .map(messageDto -> messageConvertor.toMessageEntity(messageDto))
                .collect(Collectors.toList());
        dialogEntity.setMessageEntities(messageEntities);
        dialogEntity.setPersonEntities(personEntities);
        dialogEntity.setId(dialogDto.getId());
        return dialogEntity;
    }


}
