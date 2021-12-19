package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.convertors.MessageConvertor;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.DialogRepository;
import ru.shishlov.btf.repositories.MessageRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageConvertor messageConvertor;
    private final SimpMessagingTemplate messagingTemplate;
    private final DialogRepository dialogRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          MessageConvertor messageConvertor,
                          SimpMessagingTemplate messagingTemplate,
                          DialogRepository dialogRepository,
                          PeopleRepository peopleRepository) {
        this.messageRepository = messageRepository;
        this.messageConvertor = messageConvertor;
        this.messagingTemplate = messagingTemplate;
        this.dialogRepository = dialogRepository;
        this.peopleRepository = peopleRepository;
    }

    public Collection<MessageDto> getAllMessagesByDialogId(Long id){
        return messageRepository.findMessageEntityByDialogIdOrderByDateDesc(id).stream()
                .map(messageConvertor::toMessageDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void add(MessageDto messageDto){
        MessageEntity messageEntity = new MessageEntity();
        Optional<DialogEntity> dialogEntity = dialogRepository.findById(messageDto.getDialogId());
        Optional<PersonEntity> personEntity = peopleRepository.findByLogin(messageDto.getLoginFrom());
        System.out.println("I");
        System.out.println(dialogEntity.isPresent());
        System.out.println(personEntity.isPresent());
        System.out.println(messageDto.getLoginFrom());
        if(dialogEntity.isPresent() && personEntity.isPresent()){
            System.out.println("here");
            DialogEntity dialog = dialogEntity.get();
            messageEntity.setDialog(dialog);
            messageEntity.setText(messageDto.getText());
            messageEntity.setWhose(personEntity.get());
            messageEntity.setDate(new Timestamp(new Date().getTime()));
            messageRepository.save(messageEntity);
            dialog.getPersons().stream().filter(personEntity1 -> !personEntity1.equals(personEntity.get()))
                    .forEach(personEntity1 -> sendNotification(personEntity1, messageEntity));
        }
    }

    private void sendNotification(PersonEntity personEntity, MessageEntity messageEntity){
        System.out.println("to " + personEntity.getLogin());
        messagingTemplate.convertAndSendToUser(personEntity.getLogin(), "chat", messageConvertor.toMessageDto(messageEntity));
    }



}
