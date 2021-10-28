package ru.shishlov.btf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
    private ImageHandlerDB imageHandlerDB;
    private ImageConvertorDB imageConvertorDB;
    private ImageConvertorFS imageConvertor;
    private ImageHandlerFS imageHandlerFS;


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
    public ImageHelper imageHelper(){
        return new ImageHelper(imageHandlerDB, imageConvertorDB); //can use FS here
    }
    @Autowired
    public void setImageHandlerDB(ImageHandlerDB imageHandlerDB) {
        this.imageHandlerDB = imageHandlerDB;
    }
    @Autowired
    public void setImageConvertorDB(ImageConvertorDB imageConvertorDB) {
        this.imageConvertorDB = imageConvertorDB;
    }
    @Autowired
    public void setImageConvertor(ImageConvertorFS imageConvertor) {
        this.imageConvertor = imageConvertor;
    }
    @Autowired
    public void setImageHandlerFS(ImageHandlerFS imageHandlerFS) {
        this.imageHandlerFS = imageHandlerFS;
    }
}
