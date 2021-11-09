package ru.shishlov.btf.components.images;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.ImageDto;
import ru.shishlov.btf.entities.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ImageConvertorFS extends ImageConvertorAbs {
    /**
     *
     * @param image
     * Taking imageEntity, then taking location from it and making imageDto
     * @return ImageDto
     */

    @Override
    public ImageDto toImageDto(Image image) {
        File fileItem = new File(image.getLocation());
        try {
            FileInputStream input = new FileInputStream(fileItem);
            MultipartFile mf =  new MockMultipartFile(image.getName(),
                    fileItem.getName(), "image/png", IOUtils.toByteArray(input));
            return new ImageDto(mf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
