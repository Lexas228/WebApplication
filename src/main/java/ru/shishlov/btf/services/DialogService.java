package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.shishlov.btf.components.convertors.DialogConvertor;
import ru.shishlov.btf.components.convertors.MessageConvertor;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.DialogRepository;
import ru.shishlov.btf.repositories.MessageRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DialogService {
    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;
    private final PeopleRepository peopleRepository;
    private DialogConvertor dialogConvertor;
    private MessageConvertor messageConvertor;
    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    public DialogService(DialogRepository dialogRepository, MessageRepository messageRepository, PeopleRepository peopleRepository) {
        this.dialogRepository = dialogRepository;
        this.messageRepository = messageRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<DialogDto> findAllDialogsFor(String login){
        return dialogRepository.findAllByPersonLogin(login).stream()
                .map(dialogEntity -> dialogConvertor.toDialogDto(dialogEntity)).collect(Collectors.toList());
    }

    public List<MessageDto> findAllMessagesBetween(String loginOne, String loginTwo){
        Optional<DialogEntity> dialog = dialogRepository.findDialogBetweenTwoPerson(loginOne, loginTwo);
        if(!dialog.isPresent()){
            createDialog(loginOne, loginTwo);
        }
        return dialog.map(dialogEntity -> messageRepository.findByDialogId(dialogEntity.getId()).stream()
                .map(messageEntity -> messageConvertor.toMessageDto(messageEntity, loginOne, loginTwo)).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    public void addMessage(MessageDto messageDto){
        Optional<DialogEntity> dialog = dialogRepository.findDialogBetweenTwoPerson(messageDto.getLoginFrom(), messageDto.getLoginTo());
        DialogEntity dialogEntity;
        if(dialog.isPresent()){
            dialogEntity = dialog.get();
        }else{
            dialogEntity = createDialog(messageDto.getLoginFrom(), messageDto.getLoginTo());
            sentDialog(dialogEntity, messageDto.getLoginFrom());
            sentDialog(dialogEntity, messageDto.getLoginTo());
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setDialog(dialogEntity);
        messageEntity.setDate(messageDto.getDate());
        messageEntity.setText(messageDto.getText());
        Optional<PersonEntity> pe = peopleRepository.findByLogin(messageDto.getLoginFrom());
        pe.ifPresent(messageEntity::setWhose);
        messageRepository.save(messageEntity);
        sentMessage(messageDto);
    }

    private void sentMessage(MessageDto messageDto){
        String to = "/user/" + messageDto.getLoginTo() +"/messages";
        System.out.println(to);
        messagingTemplate.convertAndSend("/dialog/"+messageDto.getLoginTo(), messageDto);
    }

    private void sentDialog(DialogEntity dialogEntity, String to){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setLoginOfSecondUser(dialogEntity.getFirstPerson().getLogin());
        dialogDto.setLoginOfSecondUser(dialogEntity.getSecondPerson().getLogin());
        messagingTemplate.convertAndSend("/dialog/"+to, dialogDto);
    }


    private DialogEntity createDialog(String loginOne, String loginTwo){
        Optional<PersonEntity> one = peopleRepository.findByLogin(loginOne);
        Optional<PersonEntity> two = peopleRepository.findByLogin(loginTwo);
        if(one.isPresent() && two.isPresent()){
            DialogEntity dialog = new DialogEntity();
            dialog.setFirstPerson(one.get());
            dialog.setSecondPerson(two.get());
            dialogRepository.save(dialog);
            return dialog;
        }
        return null;
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
