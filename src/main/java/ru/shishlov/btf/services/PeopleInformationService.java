package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shishlov.btf.dao.PeopleInformationDao;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;

import java.util.Collection;

@Service
public class PeopleInformationService {
    private final PeopleInformationDao peopleInformationDao;
    @Autowired
    public PeopleInformationService(PeopleInformationDao peopleInformationDao){
        this.peopleInformationDao = peopleInformationDao;
    }

    public PersonInformation findByLogin(String login){
        return peopleInformationDao.findByLogin(login);
    }

    public Collection<PersonInformation> getAll(){
        return peopleInformationDao.getAll();
    }

    public void save(PersonInformation personInformation){
        peopleInformationDao.add(personInformation);
    }

    public void update(PersonInformation personInformation, String login){
        peopleInformationDao.update(login, personInformation);
    }

    public void delete(String login){
        peopleInformationDao.delete(login);
    }


}
