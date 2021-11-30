package ru.shishlov.btf.components.convertors;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.entities.DialogEntity;

@Component
public class DialogConvertor {

    public DialogDto toDialogDto(DialogEntity dialogEntity){
        DialogDto dialogDto = new DialogDto();
        dialogDto.setLoginOfFirstUser(dialogEntity.getFirstPerson().getLogin());
        dialogDto.setLoginOfSecondUser(dialogEntity.getSecondPerson().getLogin());
        return dialogDto;
    }


}
