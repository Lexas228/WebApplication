package ru.shishlov.btf.components.images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

@Component
public class ImageConvertorDB extends ImageConvertorImp {
    private ImageConvertorFS imageConvertorFS;

    @Autowired
    public void setImageConvertorFS(ImageConvertorFS imageConvertorFS) {
        this.imageConvertorFS = imageConvertorFS;
    }

    @Override
    public ImageDto toImageDto(Image image) {
        if(image.getContent() == null){
            return imageConvertorFS.toImageDto(image);
        }
        MultipartFile file = new MockMultipartFile(image.getName(), image.getContent());
        return new ImageDto(file);

    }
}
