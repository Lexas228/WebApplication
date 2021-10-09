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
import ru.shishlov.btf.model.Person;

import java.util.HashSet;
import java.util.List;

@Service
public class PeopleService implements UserDetailsService{
    private final PeopleDao peopleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void passwordEncoder(PasswordEncoder PasswordEncoder){
        this.passwordEncoder = PasswordEncoder;
    }

    @Autowired
    public PeopleService(PeopleDao peopleRepository){
        this.peopleRepository = peopleRepository;
    }


    public void save(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.add(person);
    }

    public Person findByLogin(String login){
        return peopleRepository.findByLogin(login);
    }



    public void delete(String login){
        peopleRepository.delete(login);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = peopleRepository.findByLogin(s);
        if(person == null){
            throw new UsernameNotFoundException("User was " + s + " not found");
        }
        return new User(person.getLogin(), person.getPassword(), new HashSet<>());
    }

    public boolean isCorrectPassword(Person person, String password){
        return passwordEncoder.matches(password, person.getPassword());
    }

    public void changePassword(String login, String password){
        peopleRepository.updatePassword(login, passwordEncoder.encode(password));
    }




}
