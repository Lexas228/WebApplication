package ru.shishlov.btf.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.convertors.PersonConvertor;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.dto.ResponsePersonDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.entities.PersonInformationEntity;
import ru.shishlov.btf.repositories.PeopleInformationRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PeopleService implements UserDetailsService{
    private final PeopleRepository peopleRepository;
    private final PeopleInformationRepository peopleInformationRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final PersonConvertor personConvertor;



    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder,
                         PeopleInformationRepository peopleInformationRepository, PersonConvertor personConvertor, ImageService imageService){
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.peopleInformationRepository = peopleInformationRepository;
        this.personConvertor = personConvertor;
        this.imageService = imageService;
    }

    public void updateInfo(RequestPersonDto requestPersonDto, String login){
       PersonInformationEntity old = peopleInformationRepository.findByPersonLogin(login);
       personConvertor.update(old, requestPersonDto);
       peopleInformationRepository.save(old);
    }

    public void save(RequestPersonDto person){
        PersonEntity pers = personConvertor.toPersonEntity(person);
        pers.setPassword(passwordEncoder.encode(person.getPassword()));
        pers.getPersonInformation().setLastAction(new Date());
        Image image = imageService.save(person.getImage(), person.getLogin());
        pers.getPersonInformation().setImage(image);
        peopleRepository.save(pers);
    }

    @Transactional
    public void updateLastAction(String login){
       // peopleInformationRepository.updateLastAction(new java.sql.Date(new Date().getTime()), login);
        peopleInformationRepository.findByPersonLogin(login).setLastAction(new Date());
    }

    @Transactional
    public Collection<ResponsePersonDto> getAll(){
        return peopleRepository.findAll().stream().map(personConvertor::toResponsePersonDto).collect(Collectors.toList());
    }

    public ResponsePersonDto findByLogin(String login){
        Optional<PersonEntity> pe = peopleRepository.findByLogin(login);
        return pe.map(personConvertor::toResponsePersonDto).orElse(null);
    }

    public void delete(String login){
        peopleRepository.deleteByLogin(login);
        imageService.cleanAfterDeleting(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<PersonEntity> person = peopleRepository.findByLogin(s);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("User " + s + " was not found");
        }
        PersonEntity ps = person.get();
        return new User(ps.getLogin(), ps.getPassword(), new HashSet<>());
    }

    private String getPasswordByLogin(String login){
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

}
