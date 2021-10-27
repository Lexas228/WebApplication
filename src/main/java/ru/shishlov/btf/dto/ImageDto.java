package ru.shishlov.btf.dto;

import antlr.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class ImageDto {
    private MultipartFile file;

    public ImageDto(MultipartFile file) {
        this.file = file;
    }

    public ImageDto() {
    }

    public MultipartFile getFile(){
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getName() {
        return file.getOriginalFilename();
    }


    public byte[] getBytes() {
        try {
            return file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmpty(){
        return file.isEmpty();
    }
}
