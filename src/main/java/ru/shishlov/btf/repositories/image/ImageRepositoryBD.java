package ru.shishlov.btf.repositories.image;

import org.springframework.stereotype.Repository;
import ru.shishlov.btf.entities.Image;

import java.util.Optional;


//saving images in database whole.(just jpa wrapper)
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
    public void clean(String login) {
        //We don't have anything except data in bd which we have already deleted to this moment
        //So we do anything here
    }

    @Override
    public Optional<Image> findById(long id) {
        return imageRepositoryJpa.findById(id);
    }
}
