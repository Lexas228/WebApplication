package ru.shishlov.btf.components.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

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
        return getNeedConvertorForDto(imageDto).toImageEntity(imageDto);
    }

    @Override
    public ImageDto toImageDto(Image image) {
        return getNeedConvertorForEntity(image).toImageDto(image);
    }

    private ImageConvertor getNeedConvertorForDto(ImageDto image){
        return imageConvertorDB;//not important in our case
    }

    private ImageConvertor getNeedConvertorForEntity(Image image){
        return image.getContent() == null ? imageConvertorFS : imageConvertorDB;
    }


}
