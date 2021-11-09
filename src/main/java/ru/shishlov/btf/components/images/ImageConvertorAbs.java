package ru.shishlov.btf.components.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

/**
 * This class is needed to avoid repeating code in ImageConvertors
 */
@Component
public abstract class ImageConvertorAbs implements ImageConvertor {
    private ImageDto commonImage;

    @Autowired
    public void setCommonImage(ImageDto commonImage) {
        this.commonImage = commonImage;
    }

    /**
     * @param imageDto
     * Taking imageDto and convert it to Entity
     * If imageDto is null we will take commonImageDto which has common photo
     * @return ImageEntity
     */
    @Override
    public Image toImageEntity(ImageDto imageDto) {
        ImageDto need = imageDto == null || imageDto.isEmpty() ? commonImage : imageDto;
        Image im = new Image();
        im.setName(need.getName());
        im.setContent(need.getBytes());
        return im;
    }
}
