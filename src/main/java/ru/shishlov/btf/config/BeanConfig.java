package ru.shishlov.btf.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.ImageDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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

    @Bean
    public Validator validator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

}
