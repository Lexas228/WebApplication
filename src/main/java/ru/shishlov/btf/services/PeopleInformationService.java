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

    public PersonInformation findByPersonId(long id){
        return peopleInformationDao.findByPersonId(id);
    }

    public Collection<PersonInformation> getAll(){
        return peopleInformationDao.getAll();
    }

    public void save(PersonInformation personInformation){
        peopleInformationDao.add(personInformation);
    }

    public void update(PersonInformation personInformation, long personId){
        peopleInformationDao.update(personId, personInformation);
    }

    public void delete(long personId){
        peopleInformationDao.delete(personId);
    }


}
