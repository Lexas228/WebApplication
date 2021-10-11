package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.dao.PeopleDao;
import ru.shishlov.btf.dao.PeopleInformationDao;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

@Service
public class PeopleService implements UserDetailsService{
    private final PeopleDao peopleRepository;
    private final PeopleInformationService peopleInformationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleDao peopleRepository, PeopleInformationService peopleInformationService, PasswordEncoder passwordEncoder){
        this.peopleRepository = peopleRepository;
        this.peopleInformationService = peopleInformationService;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateInfo(PersonInformation personInformation, String login){
        Person p = peopleRepository.findByLogin(login);
        if(p != null)
        peopleInformationService.update(personInformation, p.getId());
    }

    public void save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        long userId = peopleRepository.add(person);
        PersonInformation pr = person.getPersonInformation();
        pr.setPersonId(userId);
        peopleInformationService.save(pr);
    }

    public Collection<Person> getAll(){
        Collection<Person> personCollection = peopleRepository.getAll();
        personCollection.forEach(person -> {
            person.setPersonInformation(peopleInformationService.findByPersonId(person.getId()));
        });
        return personCollection;
    }

    public Person findByLogin(String login){
        Person p = peopleRepository.findByLogin(login);
        if(p != null) {
            PersonInformation inf = peopleInformationService.findByPersonId(p.getId());
            p.setPersonInformation(inf);
        }
        return p;
    }



    public void delete(String login){
        peopleRepository.delete(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = peopleRepository.findByLogin(s);
        if(person == null){
            throw new UsernameNotFoundException("User " + s + " was not found");
        }
        return new User(person.getLogin(), person.getPassword(), new HashSet<>());
    }

    public String getPasswordByLogin(String login){
        return peopleRepository.findByLogin(login).getPassword();
    }

    public void changePassword(String login, String password){
        peopleRepository.updatePassword(login, passwordEncoder.encode(password));
    }

    public boolean isCorrectPassword(String login, String rawPassword){
        String pass = getPasswordByLogin(login);
        return passwordEncoder.matches(rawPassword, pass);
    }




}
