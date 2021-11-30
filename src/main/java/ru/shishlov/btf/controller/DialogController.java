package ru.shishlov.btf.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.services.DialogService;


import java.security.Principal;

@Controller
public class DialogController {
    private final DialogService dialogService;

    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @GetMapping("dialog/{login}")
    public String getMyMessagesWith(@PathVariable String login, Principal principal, Model model){
        model.addAttribute("messages", dialogService.findAllMessagesBetween(login, principal.getName()));
        model.addAttribute("with", login);
        model.addAttribute("who", principal.getName());
        return "messages/dialog";
    }

    @MessageMapping("/dialog")
    public void sendMessage(MessageDto messageDto){
        System.out.println("here");
        dialogService.addMessage(messageDto);
    }



}
