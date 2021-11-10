package ru.shishlov.btf.services;


import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.PersonConvertor;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.entities.PersonInformationEntity;
import ru.shishlov.btf.repositories.PeopleInformationRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import javax.validation.Validator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PeopleService implements UserDetailsService{
    private final PeopleRepository peopleRepository;
    private final PeopleInformationRepository peopleInformationRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final PersonConvertor personConvertor;
    private Validator validator;



    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder,
                         PeopleInformationRepository peopleInformationRepository, PersonConvertor personConvertor, ImageService imageService){
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.peopleInformationRepository = peopleInformationRepository;
        this.personConvertor = personConvertor;
        this.imageService = imageService;
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void updateInfo(PersonInformationDto personInformation, String login){
       PersonInformationEntity old = peopleInformationRepository.findByPersonLogin(login);
       old.setAddress(personInformation.getAddress());
       old.setBirthday(personInformation.getBirthday());
       old.setInformation(personInformation.getInformation());
       old.setName(personInformation.getName());
       old.setSurname(personInformation.getSurname());
       if(!personInformation.getImage().isEmpty()){
            imageService.update(personInformation.getImage(), old.getImage().getId());
       }
       peopleInformationRepository.save(old);
    }

    public PersonInformationDto findInfoByLogin(String login){
        return personConvertor.toPersonInformationDto(peopleInformationRepository.findByPersonLogin(login));
    }

    public void save(PersonDto person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        PersonEntity entity = personConvertor.toPersonEntity(person);
        PersonInformationEntity pers = entity.getPersonInformation();
        Image image = imageService.save(person.getPersonInformation().getImage(), person.getLogin());
        pers.setImage(image);
        peopleRepository.save(entity);
    }

    public Collection<PersonDto> getAll(){
        return peopleRepository.findAll().stream().map(personConvertor::toPersonDto).collect(Collectors.toList());
    }

    public PersonDto findByLogin(String login){
        Optional<PersonEntity> pe = peopleRepository.findByLogin(login);
        return pe.map(personConvertor::toPersonDto).orElse(null);
    }

    public void delete(String login){
        peopleRepository.deleteByLogin(login);
        imageService.cleanAfterDeleting(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<PersonEntity> person = peopleRepository.findByLogin(s);
        if(!person.isPresent()){
            throw new UsernameNotFoundException("User " + s + " was not found");
        }
        PersonEntity ps = person.get();
        return new User(ps.getLogin(), ps.getPassword(), new HashSet<>());
    }

    public String getPasswordByLogin(String login){
        Optional<PersonEntity> pe = peopleRepository.findByLogin(login);
        return pe.map(PersonEntity::getPassword).orElse(null);
    }

    public void changePassword(String login, String password){
        peopleRepository.updatePasswordByLogin(passwordEncoder.encode(password), login);
    }

    public boolean isCorrectPassword(String login, String rawPassword){
        String pass = getPasswordByLogin(login);
        return passwordEncoder.matches(rawPassword, pass);
    }

    public boolean isAvailableLogin(String login){
        return !peopleRepository.existsByLogin(login);
    }

    public boolean isAvailableToSave(PersonDto person){
        var l = validator.validate(person);
        if(l.isEmpty()){
            return isAvailableLogin(person.getLogin());
        }
        return false;
    }




}
