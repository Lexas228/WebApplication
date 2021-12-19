package ru.shishlov.btf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.services.DialogService;

import javax.validation.Valid;
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

    @GetMapping
    public List<DialogDto> getAll(Principal principal){
        return dialogService.findAllDialogsFor(principal.getName());
    }

    @PostMapping
    public DialogDto create(@RequestBody @Valid DialogDto dialogDto, Principal principal){
        return dialogService.create(dialogDto, principal.getName());
    }

    //we don't have to delete it for two users but we have to refactor a big part so let's just delete it
    //but we have to check if this dialog contains this uers
    @PreAuthorize("@messageAccessValidator.check(#dialogId, authentication)")
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long dialogId){
        return dialogService.delete(dialogId) ? ResponseEntity.ok("Was deleted") : ResponseEntity.badRequest().body("Not found");
    }


}
