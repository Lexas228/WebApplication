package ru.shishlov.btf.components.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.components.convertors.images.ImageConvertorBoss;
import ru.shishlov.btf.dto.PersonDto;
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
            PersonInformationEntity personInformationEntity = new PersonInformationEntity();
            personInformationEntity.setPerson(personEntity);
            personEntity.setPersonInformation(personInformationEntity);
            personEntity.setPassword(personDto.getPassword());
            personInformationEntity.setInformation(personDto.getInformation());
            personInformationEntity.setName(personDto.getName());
            personInformationEntity.setSurname(personDto.getSurname());
            personInformationEntity.setAddress(personDto.getAddress());
            personInformationEntity.setBirthday(personDto.getBirthday());
            personInformationEntity.setLastAction(personDto.getLastAction());
         //   personInformationEntity.setImage(imageConvertorBoss.toImageEntity(personDto.getImage()));
        }
        return personEntity;
    }

    public PersonDto toPersonDto(PersonEntity personEntity){
        PersonDto personDto = null;
        if(personEntity != null) {
            personDto = new PersonDto();
            personDto.setLogin(personEntity.getLogin());
            personDto.setPassword("secret");
            PersonInformationEntity personInformation = personEntity.getPersonInformation();
            personDto.setName(personInformation.getName());
            personDto.setSurname(personInformation.getSurname());
            personDto.setAddress(personInformation.getAddress());
          //  personDto.setImage(imageConvertorBoss.toImageDto(personInformation.getImage()));
            personDto.setBirthday(personInformation.getBirthday());
            personDto.setLastAction(personInformation.getLastAction());
            personDto.setInformation(personInformation.getInformation());

        }
        return personDto;
    }

    public void update(PersonInformationEntity informationEntity, PersonDto personDto){
        informationEntity.setInformation(personDto.getInformation());
      //  informationEntity.setImage(imageConvertorBoss.toImageEntity(personDto.getImage()));
        informationEntity.setName(personDto.getName());
        informationEntity.setSurname(personDto.getSurname());
        informationEntity.setBirthday(personDto.getBirthday());
        informationEntity.setAddress(personDto.getAddress());
    }
}
