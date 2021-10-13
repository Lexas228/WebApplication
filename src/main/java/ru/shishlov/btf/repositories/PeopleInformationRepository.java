package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.PersonInformationEntity;
@Repository
public interface PeopleInformationRepository extends JpaRepository<PersonInformationEntity, Long> {
    PersonInformationEntity findPersonInformationByPersonId(long id);

}
