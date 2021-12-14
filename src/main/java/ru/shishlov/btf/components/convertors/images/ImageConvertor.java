package ru.shishlov.btf.components.convertors.images;

import org.springframework.stereotype.Component;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

@Component
public interface ImageConvertor {
    Image toImageEntity(ImageDto imageDto);
    ImageDto toImageDto(Image image);
}
