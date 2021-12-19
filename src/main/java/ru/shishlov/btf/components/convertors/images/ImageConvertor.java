package ru.shishlov.btf.components.convertors.images;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.entities.Image;

@Component
public interface ImageConvertor {
    Image toImageEntity(MultipartFile image);
    byte[] toByteArray(Image image);
}
