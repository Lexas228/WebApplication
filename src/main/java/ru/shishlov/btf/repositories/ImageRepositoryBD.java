package ru.shishlov.btf.repositories;

import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

import java.util.Optional;

@Repository
public class ImageRepositoryBD implements ImageRepository{
    private final ImageRepositoryJpa imageRepositoryJpa;

    public ImageRepositoryBD(ImageRepositoryJpa imageRepositoryJpa) {
        this.imageRepositoryJpa = imageRepositoryJpa;
    }

    @Override
    public void save(Image image, String login) {
        imageRepositoryJpa.save(image);
    }

    @Override
    public void update(Image image) {
        imageRepositoryJpa.save(image);
    }

    @Override
    public void clean(String login) {
        //do nothing actually
    }

    @Override
    public Optional<Image> findById(long id) {
        return imageRepositoryJpa.findById(id);
    }
}
