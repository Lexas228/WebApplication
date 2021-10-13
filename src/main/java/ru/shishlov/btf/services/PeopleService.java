package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.components.Transformator;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.entities.PersonEntity;
import ru.shishlov.btf.entities.PersonInformationEntity;
import ru.shishlov.btf.repositories.PeopleInformationRepository;
import ru.shishlov.btf.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


@Service
@Transactional
public class PeopleService implements UserDetailsService{
    private final PeopleRepository peopleRepository;
    private final PeopleInformationRepository peopleInformationRepository;
    private final PasswordEncoder passwordEncoder;
    private final Transformator transformator;



    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, PeopleInformationRepository peopleInformationRepository, Transformator transformator){
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.peopleInformationRepository = peopleInformationRepository;
        this.transformator = transformator;
    }

    public void updateInfo(PersonInformationDto personInformation, String login){
        PersonEntity p = peopleRepository.findByLogin(login);
        PersonInformationEntity personInformationEntity = transformator.toPersonInformationEntity(personInformation);
        personInformationEntity.setPerson(p);
        personInformationEntity.setId(p.getPersonInformation().getId());
        peopleInformationRepository.save(personInformationEntity);
    }

    public void save(PersonDto person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(transformator.toPersonEntity(person));
    }

    public Collection<PersonDto> getAll(){
        return peopleRepository.findAll().stream().map(transformator::toPersonDto).collect(Collectors.toList());
    }

    public PersonDto findByLogin(String login){
        return transformator.toPersonDto(peopleRepository.findByLogin(login));
    }

    public void delete(String login){
        peopleRepository.deleteByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonEntity person = peopleRepository.findByLogin(s);
        if(person == null){
            throw new UsernameNotFoundException("User " + s + " was not found");
        }
        return new User(person.getLogin(), person.getPassword(), new HashSet<>());
    }

    public String getPasswordByLogin(String login){
        return peopleRepository.findByLogin(login).getPassword();
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
