package ru.shishlov.btf.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.convertors.images.ImageConvertorBoss;
import ru.shishlov.btf.entities.Image;
import ru.shishlov.btf.entities.PersonInformationEntity;
import ru.shishlov.btf.repositories.PeopleInformationRepository;
import ru.shishlov.btf.repositories.PeopleRepository;
import ru.shishlov.btf.repositories.image.ImageRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    private ImageRepository repository;
    private final ImageConvertorBoss imageConvertorBoss;
    private final PeopleInformationRepository peopleInformationRepository;


    public ImageService(@Qualifier("imageRepositoryBD") ImageRepository repository,
                        ImageConvertorBoss imageConvertorBoss, PeopleInformationRepository peopleInformationRepository) {
        this.repository = repository;
        this.imageConvertorBoss = imageConvertorBoss;
        this.peopleInformationRepository = peopleInformationRepository;
    }

    public void setRepository(ImageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Image save(MultipartFile file, String login){
        Image im = imageConvertorBoss.toImageEntity(file);
        if(im != null) {
            repository.save(im, login);
            PersonInformationEntity personInformationEntity = peopleInformationRepository.findByPersonLogin(login);
            personInformationEntity.setImage(im);
        }
        return im;
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
