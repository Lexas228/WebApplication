package ru.shishlov.btf.repositories.image;

import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

import java.util.Optional;

@Repository
public interface ImageRepository {
    void save(Image image, String login);
    void clean(String login);
    void update(Image image);
    Optional<Image> findById(long id);



}
