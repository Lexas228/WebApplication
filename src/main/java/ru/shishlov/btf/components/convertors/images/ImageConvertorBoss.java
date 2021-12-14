package ru.shishlov.btf.components.convertors.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.ImageDto;
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
    public Image toImageEntity(ImageDto imageDto) {
        if(imageDto == null) return null;
        return getNeedConvertorForDto(imageDto).toImageEntity(imageDto);
    }

    @Override
    public ImageDto toImageDto(Image image) {
        if(image == null) return null;
        return getNeedConvertorForEntity(image).toImageDto(image);
    }

    private ImageConvertor getNeedConvertorForDto(ImageDto image){
        return imageConvertorDB;//not important in our case
    }

    private ImageConvertor getNeedConvertorForEntity(Image image){
        return image.getContent() == null ? imageConvertorFS : imageConvertorDB;
    }


}
