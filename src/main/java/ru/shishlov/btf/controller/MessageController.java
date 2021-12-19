package ru.shishlov.btf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.components.security.MessageAccessValidator;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.services.MessageService;

import java.util.Collection;

@RestController
@RequestMapping("dialog/{dialogId}/message")
@CrossOrigin
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PreAuthorize("@messageAccessValidator.check(#dialogId, authentication)")
    @GetMapping
    public Collection<MessageDto> getMessages(@PathVariable Long dialogId){
        return messageService.getAllMessagesByDialogId(dialogId);
    }

    @MessageMapping("/chat")
    public void sendMessage(MessageDto messageDto){

    }

    @PostMapping
    public void save(@RequestBody MessageDto messageDto){
        messageService.add(messageDto);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable(name = "id") Long id, @PathVariable Long dialogId, DialogDto dialogDto){

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id, @PathVariable Long dialogId){

    }

}
