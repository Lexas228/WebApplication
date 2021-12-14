package ru.shishlov.btf.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.services.DialogService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dialog")
@CrossOrigin
public class DialogController {
    private final DialogService dialogService;

    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @PostAuthorize("returnObject.peopleLogins.contains(principal.username)")
    @GetMapping("/{id}")
    public DialogDto getDialog(@PathVariable Long id){
        return dialogService.getDialogById(id);
    }

    @GetMapping
    public List<DialogDto> getAll(Principal principal){
        return dialogService.findAllDialogsFor(principal.getName());
    }

    @PostMapping
    public DialogDto create(DialogDto dialogDto){
        return dialogService.create(dialogDto);
    }
}
