package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement
@JsonIgnoreProperties(value = { "image" })
public class PersonInformationDto {

    @Size(min = 2, max = 100)
    private String name;

    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull
    private String information;

    @Size(min = 2, max = 100)
    private String address;

    private ImageDto image;

    public String generateBase64Image(){
        return Base64.encodeBase64String(image.getBytes());
    }
}
