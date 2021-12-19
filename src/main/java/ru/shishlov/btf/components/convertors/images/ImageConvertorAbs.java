package ru.shishlov.btf.components.convertors.images;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.entities.Image;

import java.io.IOException;

/**
 * This class is needed to avoid repeating code in ImageConvertors
 */
@Component
public abstract class ImageConvertorAbs implements ImageConvertor {

    /**
     * Taking multipartFile and convert it to image entity
     * If imageDto is null we will take commonImageDto which has common photo
     * @return ImageEntity
     */
    @Override
    public Image toImageEntity(MultipartFile file) {
        if(file == null) return null;
        Image im = new Image();
        im.setName(file.getName());
        try {
            im.setContent(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return im;
    }
}
