package ru.shishlov.btf.components.convertors.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.entities.Image;

/**
 * This class can decide what type of convert to do
 */
@Component
public class ImageConvertorBoss implements ImageConvertor{
    private final ImageConvertorDB imageConvertorDB;
    private final ImageConvertorFS imageConvertorFS;

    @Autowired
    public ImageConvertorBoss(ImageConvertorDB imageConvertorDB, ImageConvertorFS imageConvertorFS) {
        this.imageConvertorDB = imageConvertorDB;
        this.imageConvertorFS = imageConvertorFS;
    }

    @Override
    public Image toImageEntity(MultipartFile image) {
        if(image == null) return null;
        return getNeedConvertorForDto(image).toImageEntity(image);
    }

    @Override
    public byte[] toByteArray(Image image) {
        if (image==null) return new byte[]{};
        return getNeedConvertorForEntity(image).toByteArray(image);
    }

    private ImageConvertor getNeedConvertorForDto(MultipartFile image){
        return imageConvertorDB;//not important in our case
    }

    private ImageConvertor getNeedConvertorForEntity(Image image){
        return image.getContent() == null ? imageConvertorFS : imageConvertorDB;
    }


}
