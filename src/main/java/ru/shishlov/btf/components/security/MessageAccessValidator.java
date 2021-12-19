package ru.shishlov.btf.components.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.services.DialogService;

@Component
public class MessageAccessValidator {
    private final DialogService dialogService;

    public MessageAccessValidator(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    public boolean check(Long dialogId, Authentication authentication) {
        if(dialogId == null || authentication==null)
            return false;
        return dialogService.containUserInDialog(dialogId, authentication.getName());
    }
}
