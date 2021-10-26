package ru.shishlov.btf.components.images;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.entities.Image;

@Component

public class ImageHelperDB implements ImageHelper {
    @Override
    public void prepareForSave(Image image, String login) {

    }

    @Override
    public void prepareForUpdate(Image image) {

    }
}
