package ru.shishlov.btf.components.images;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.entities.Image;

@Component
public interface ImageHandler {
    void prepareForSave(Image image, String login);
    void prepareForUpdate(Image image);
    void prepareForDelete(String login);
}
