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
        System.out.println("--------------------");
        System.out.println("Person was created his login is: " + person.getLogin());
        System.out.println("Password is " + person.getPassword());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        System.out.println("Encrypted password is " + person.getPassword());
        System.out.println("-----------------------");
        peopleRepository.add(person);
    }

    public Person findByLogin(String login){
        return peopleRepository.findByLogin(login);
    }

    public List<Person> getAll(){
        return peopleRepository.getAll();
    }

    public void update(Person person, String login){
        peopleRepository.update(login, person);
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

    public void changePassword(Person person, String password){
        person.setPassword(passwordEncoder.encode(password));
        peopleRepository.update(person.getLogin(), person);
    }




}
