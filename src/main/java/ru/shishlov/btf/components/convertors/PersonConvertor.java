package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.components.images.ImageConvertorBoss;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.entities.PersonInformationEntity;

/**
 * Class for converting from PersonEntity, PersonInformation to Dto and vice versa
 */
@Component
public class PersonConvertor {
    private final ImageConvertorBoss imageConvertorBoss;//as we have image in personInformation we have to convert it too

    @Autowired
    public PersonConvertor(ImageConvertorBoss imageConvertorBoss){
        this.imageConvertorBoss = imageConvertorBoss;
    }
    public PersonEntity toPersonEntity(PersonDto personDto){
        PersonEntity personEntity = null;
        if(personDto != null) {
            personEntity = new PersonEntity();
            personEntity.setLogin(personDto.getLogin());
            PersonInformationEntity personInformationEntity = toPersonInformationEntity(personDto.getPersonInformation());
            personInformationEntity.setPerson(personEntity);
            personEntity.setPersonInformation(personInformationEntity);
            personEntity.setPassword(personDto.getPassword());
        }
        return personEntity;
    }

    public PersonDto toPersonDto(PersonEntity personEntity){
        PersonDto personDto = null;
        if(personEntity != null) {
            personDto = new PersonDto();
            personDto.setLogin(personEntity.getLogin());
            personDto.setPersonInformation(toPersonInformationDto(personEntity.getPersonInformation()));
            personDto.setPassword(personEntity.getPassword());
        }
        return personDto;
    }

    public PersonInformationDto toPersonInformationDto(PersonInformationEntity personInformationEntity){
        PersonInformationDto personInformationDto = null;
        if(personInformationEntity != null) {
            personInformationDto = new PersonInformationDto();
            personInformationDto.setName(personInformationEntity.getName());
            personInformationDto.setSurname(personInformationEntity.getSurname());
            personInformationDto.setInformation(personInformationEntity.getInformation());
            personInformationDto.setAddress(personInformationEntity.getAddress());
            personInformationDto.setBirthday(personInformationEntity.getBirthday());
            personInformationDto.setImage(imageConvertorBoss.toImageDto(personInformationEntity.getImage()));
            personInformationDto.setLastAction(personInformationEntity.getLastAction());
        }
        return personInformationDto;
    }

    public PersonInformationEntity toPersonInformationEntity(PersonInformationDto personInformationDto){
        PersonInformationEntity personInformationEntity = null;
        if(personInformationDto != null) {
            personInformationEntity = new PersonInformationEntity();
            personInformationEntity.setName(personInformationDto.getName());
            personInformationEntity.setSurname(personInformationDto.getSurname());
            personInformationEntity.setInformation(personInformationDto.getInformation());
            personInformationEntity.setAddress(personInformationDto.getAddress());
            personInformationEntity.setBirthday(personInformationDto.getBirthday());
            personInformationEntity.setLastAction(personInformationDto.getLastAction());
        }
        return personInformationEntity;
    }

}
