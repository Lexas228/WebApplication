package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.convertors.images.ImageConvertorBoss;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.repositories.image.ImageRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository repository;
    private final ImageConvertorBoss imageConvertorBoss;


    public ImageService(@Qualifier("imageRepositoryBD") ImageRepository repository, ImageConvertorBoss imageConvertorBoss) {
        this.repository = repository;
        this.imageConvertorBoss = imageConvertorBoss;
    }

    public void setRepository(ImageRepository repository) {
        this.repository = repository;
    }

    public Image save(MultipartFile file, String login){
        Image im = imageConvertorBoss.toImageEntity(file);
        if(im != null) {
            repository.save(im, login);
        }
        return im;
    }

    public void update(MultipartFile file, Long id){
        Optional<Image> image = repository.findById(id);
        if(image.isPresent()) {
            try {
                Image im = image.get();
                im.setContent(file.getBytes());
                im.setName(file.getName());
                repository.update(im);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
