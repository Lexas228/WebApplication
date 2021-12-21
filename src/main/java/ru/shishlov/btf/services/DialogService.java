package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.convertors.DialogConvertor;
import ru.shishlov.btf.dto.DialogDto;
import ru.shishlov.btf.entities.DialogEntity;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.repositories.DialogRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DialogService {
    private final DialogRepository dialogRepository;
    private final PeopleRepository peopleRepository;
    private DialogConvertor dialogConvertor;


    @Autowired
    public DialogService(DialogRepository dialogRepository, PeopleRepository peopleRepository) {
        this.dialogRepository = dialogRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<DialogDto> findAllDialogsFor(String login){
        return dialogRepository.findAllByPersonLogin(login).stream()
                .map(dialogEntity -> dialogConvertor.toDialogDto(dialogEntity, login)).collect(Collectors.toList());
    }

    @Transactional
    public DialogDto getDialogById(Long id, String userLogin){
        return dialogConvertor.toDialogDto(dialogRepository.getById(id), userLogin);
    }

    @Transactional
    public DialogDto create(DialogDto dialogDto, String userLogin){
        DialogEntity dialogEntity = new DialogEntity();
        Optional<PersonEntity> one = peopleRepository.findByLogin(dialogDto.getWithWhom());
        Optional<PersonEntity> two = peopleRepository.findByLogin(userLogin);
        if(one.isPresent() && two.isPresent()){
            Optional<DialogEntity> dig = dialogRepository.findDialogByTwoLogin(dialogDto.getWithWhom(), userLogin);
            if(dig.isPresent()){
                return dialogConvertor.toDialogDto(dig.get(), userLogin);
            }
            Set<PersonEntity> personEntities = new HashSet<>();
            personEntities.add(one.get());
            personEntities.add(two.get());
            dialogEntity.setPersons(personEntities);
            dialogRepository.save(dialogEntity);
        }
        dialogDto.setId(dialogEntity.getId());
        return dialogDto;
    }


    public void delete(DialogDto dialogDto){
        dialogRepository.deleteById(dialogDto.getId());
    }

    @Autowired
    public void setDialogConvertor(DialogConvertor dialogConvertor) {
        this.dialogConvertor = dialogConvertor;
    }

    public boolean containUserInDialog(Long dialogId, String username){
        return dialogRepository.containsByDialogIdAndPersonLogin(dialogId, username).isPresent();
    }

    public boolean delete(Long id){
        dialogRepository.deleteById(id);
        return true;
    }



}
