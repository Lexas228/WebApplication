package ru.shishlov.btf.components.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

@Component
public abstract class ImageConvertorImp implements ImageConvertor {
    private ImageDto commonImage;

    @Autowired
    public void setCommonImage(ImageDto commonImage) {
        this.commonImage = commonImage;
    }

    @Override
    public Image toImageEntity(ImageDto imageDto) {
        ImageDto need = imageDto == null || imageDto.isEmpty() ? commonImage : imageDto;
        Image im = new Image();
        im.setName(need.getName());
        im.setContent(need.getBytes());
        return im;
    }
}
