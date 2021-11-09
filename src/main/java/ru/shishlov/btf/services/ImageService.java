package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shishlov.btf.components.images.ImageConvertorBoss;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.repositories.ImageRepository;

import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository repository;
    private final ImageConvertorBoss imageConvertorBoss;


    public ImageService(@Qualifier("imageRepositoryBD") ImageRepository repository, ImageConvertorBoss imageConvertorBoss) {
        this.repository = repository;
        this.imageConvertorBoss = imageConvertorBoss;
    }


    public Image save(ImageDto dto, String login){
        Image im = imageConvertorBoss.toImageEntity(dto);
        repository.save(im, login);
        return im;
    }

    public void update(ImageDto dto, Long id){
        Optional<Image> image = repository.findById(id);
        if(image.isPresent()) {
            Image im = image.get();
            im.setContent(dto.getBytes());
            im.setName(dto.getName());
            repository.update(im);
        }
    }

    /**
     *
     * @param login
     * cleaning after deleting, actually only in that case when with use file system saving.
     */
    public void cleanAfterDeleting(String login){
        repository.clean(login);
    }




}
