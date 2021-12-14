package ru.shishlov.btf.components.convertors.images;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

@Component
public class ImageConvertorDB extends ImageConvertorAbs {
    /**
     *
     * @param image
     * Taking image and make dto from that, only in that case
     * if we have bytes in it
     * @return ImageDto
     */
    @Override
    public ImageDto toImageDto(Image image) {
        MultipartFile file = new MockMultipartFile(image.getName(), image.getContent());
        return new ImageDto(file);

    }
}
