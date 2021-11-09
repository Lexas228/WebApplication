package ru.shishlov.btf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.images.*;
import ru.shishlov.btf.dto.ImageDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class BeanConfig {
    @Value(value = "${images.path}")
    private String imagePath;
    @Value(value = "${common.image.name}")
    private String commonImageName;


    @Description("bean for common image if user didn't chose his one")
    @Bean
    public ImageDto imageDto(){
        File fileItem = new File(imagePath + commonImageName);
        try {
            FileInputStream  input = new FileInputStream(fileItem);
            MultipartFile mf =  new MockMultipartFile("fileItem",
                    fileItem.getName(), "image/png", IOUtils.toByteArray(input));
            return new ImageDto(mf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
