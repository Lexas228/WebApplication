package ru.shishlov.btf.components.images;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.entities.Image;

@Component
public class ImageHandlerDB implements ImageHandler {
    @Override
    public void prepareForSave(Image image, String login) {

    }

    @Override
    public void prepareForUpdate(Image image) {

    }

    @Override
    public void prepareForDelete(String login) {

    }
}
