package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.shishlov.btf.components.images.ImageHelper;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.repositories.ImageRepository;

import java.util.Optional;

@Service
@PropertySource(value = "classpath:application.properties")
public class ImageService {
    private final ImageRepository repository;
    private final ImageHelper imageHelper;

    @Autowired
    public ImageService(ImageRepository repository, ImageHelper imageHelper) {
        this.repository = repository;
        this.imageHelper = imageHelper;
    }

    public Image save(ImageDto dto, String login){
        Image im = imageHelper.getImageConvertor().toImageEntity(dto);
        imageHelper.getImageHandler().prepareForSave(im, login);
        repository.save(im);
        return im;
    }

    public void update(ImageDto dto, Long id){
        Optional<Image> image = repository.findById(id);
        if(image.isPresent()) {
            Image im = image.get();
            im.setContent(dto.getBytes());
            im.setName(dto.getName());
            imageHelper.getImageHandler().prepareForUpdate(im);
            repository.save(im);
        }
    }

    public void delete(String login){
        imageHelper.getImageHandler().prepareForDelete(login);
    }




}
