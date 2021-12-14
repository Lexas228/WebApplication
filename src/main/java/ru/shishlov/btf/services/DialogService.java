package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.convertors.DialogConvertor;
import ru.shishlov.btf.components.convertors.MessageConvertor;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.DialogRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DialogService {
    private final DialogRepository dialogRepository;
    private final PeopleRepository peopleRepository;
    private DialogConvertor dialogConvertor;
    private MessageConvertor messageConvertor;
    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    public DialogService(DialogRepository dialogRepository, PeopleRepository peopleRepository) {
        this.dialogRepository = dialogRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<DialogDto> findAllDialogsFor(String login){
        return dialogRepository.findAllByPersonLogin(login).stream()
                .map(dialogEntity -> dialogConvertor.toDialogDto(dialogEntity)).collect(Collectors.toList());
    }

    public DialogDto getDialogById(Long id){
        return dialogConvertor.toDialogDto(dialogRepository.getById(id));
    }

    public DialogDto create(DialogDto dialogDto){
        dialogDto.setId(null);
        DialogEntity entity = dialogConvertor.toDialogEntity(dialogDto);
        dialogRepository.save(entity);
        dialogDto.setId(entity.getId());
        return dialogDto;
    }

    @Transactional
    public void addMessage(MessageDto messageDto){
      dialogRepository.getById(messageDto.getDialogId()).getMessageEntities().add(messageConvertor.toMessageEntity(messageDto));
    }

    private void sentMessage(MessageDto messageDto){
    }

    private void sentDialog(DialogEntity dialogEntity, String to){
        DialogDto dialogDto = new DialogDto();
        messagingTemplate.convertAndSend("/dialog/"+to, dialogDto);
    }


    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setDialogConvertor(DialogConvertor dialogConvertor) {
        this.dialogConvertor = dialogConvertor;
    }

    @Autowired
    public void setMessageConvertor(MessageConvertor messageConvertor) {
        this.messageConvertor = messageConvertor;
    }


}
