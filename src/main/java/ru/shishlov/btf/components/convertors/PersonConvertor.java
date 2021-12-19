package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.components.convertors.images.ImageConvertorBoss;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.dto.ResponsePersonDto;
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

    public PersonEntity toPersonEntity(RequestPersonDto requestPersonDto){
        PersonEntity personEntity = null;
        if(requestPersonDto != null) {
            personEntity = new PersonEntity();
            personEntity.setLogin(requestPersonDto.getLogin());
            PersonInformationEntity personInformationEntity = new PersonInformationEntity();
            personInformationEntity.setPerson(personEntity);
            personEntity.setPersonInformation(personInformationEntity);
            personEntity.setPassword(requestPersonDto.getPassword());
            personInformationEntity.setInformation(requestPersonDto.getInformation());
            personInformationEntity.setName(requestPersonDto.getName());
            personInformationEntity.setSurname(requestPersonDto.getSurname());
            personInformationEntity.setAddress(requestPersonDto.getAddress());
            personInformationEntity.setBirthday(requestPersonDto.getBirthday());
            personInformationEntity.setImage(imageConvertorBoss.toImageEntity(requestPersonDto.getImage()));
        }
        return personEntity;
    }

    public ResponsePersonDto toResponsePersonDto(PersonEntity personEntity){
        ResponsePersonDto responsePersonDto = null;
        if(personEntity != null) {
            responsePersonDto = new ResponsePersonDto();
            responsePersonDto.setLogin(personEntity.getLogin());
            PersonInformationEntity personInformation = personEntity.getPersonInformation();
            responsePersonDto.setName(personInformation.getName());
            responsePersonDto.setSurname(personInformation.getSurname());
            responsePersonDto.setAddress(personInformation.getAddress());
            responsePersonDto.setImage(imageConvertorBoss.toByteArray(personInformation.getImage()));
            responsePersonDto.setBirthday(personInformation.getBirthday());
            responsePersonDto.setLastAction(personInformation.getLastAction());
            responsePersonDto.setInformation(personInformation.getInformation());

        }
        return responsePersonDto;
    }

    public void update(PersonInformationEntity informationEntity, RequestPersonDto requestPersonDto){
        informationEntity.setInformation(requestPersonDto.getInformation());
      // informationEntity.setImage(imageConvertorBoss.toImageEntity(personDto.getImage()));
        informationEntity.setName(requestPersonDto.getName());
        informationEntity.setSurname(requestPersonDto.getSurname());
        informationEntity.setBirthday(requestPersonDto.getBirthday());
        informationEntity.setAddress(requestPersonDto.getAddress());
    }
}
