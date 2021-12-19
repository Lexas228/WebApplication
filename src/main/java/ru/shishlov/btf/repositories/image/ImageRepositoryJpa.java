package ru.shishlov.btf.repositories.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

import java.util.Optional;

@Repository
public interface ImageRepositoryJpa extends JpaRepository<Image, Long> {
    Optional<Image> findById(long id);
}
