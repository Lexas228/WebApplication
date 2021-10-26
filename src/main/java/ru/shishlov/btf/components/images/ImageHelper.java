package ru.shishlov.btf.components.images;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.entities.Image;

import javax.persistence.Column;

@Component
public interface ImageHelper {
    void prepareForSave(Image image, String login);
    void prepareForUpdate(Image image);
}
