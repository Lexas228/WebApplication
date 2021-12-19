package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.dto.MessageDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.MessageEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DialogConvertor {

    public DialogDto toDialogDto(DialogEntity dialogEntity, String userLogin){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setId(dialogEntity.getId());
        String withWhom = dialogEntity.getPersons().stream()
                        .map(PersonEntity::getLogin)
                        .filter(login -> !login.equals(userLogin))
                        .findAny().orElse(userLogin);
        dialogDto.setWithWhom(withWhom);
         return dialogDto;
    }
}
