package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shishlov.btf.components.images.ImageConvertor;
import ru.shishlov.btf.components.images.ImageHelper;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.repositories.ImageRepository;

import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository repository;
    private final ImageConvertor imageConvertor;
    private final ImageHelper imageHelper;

    @Autowired
    public ImageService(ImageRepository repository, @Qualifier(value = "imageConvertorFS") ImageConvertor imageConvertor,
                        @Qualifier(value = "imageHelperFS") ImageHelper imageHelper) {
        this.repository = repository;
        this.imageConvertor = imageConvertor;
        this.imageHelper = imageHelper;
    }

    public Image save(ImageDto dto, String login){
        Image im = imageConvertor.toImageEntity(dto);
        imageHelper.prepareForSave(im, login);
        repository.save(im);
        return im;
    }

    public void update(ImageDto dto, Long id){
        Optional<Image> image = repository.findById(id);
        if(image.isPresent()) {
            Image im = image.get();
            im.setContent(dto.getBytes());
            im.setName(dto.getName());
            imageHelper.prepareForUpdate(im);
            repository.save(im);
        }
    }




}
