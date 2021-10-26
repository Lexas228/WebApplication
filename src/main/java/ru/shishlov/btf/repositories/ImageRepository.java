package ru.shishlov.btf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findById(long id);
}
