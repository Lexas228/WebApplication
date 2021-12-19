package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.validators.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@XmlRootElement
public class RequestPersonDto {

    @Size(min = 2, max = 100)
    private String login;

    @Size(min = 2, max = 100)
    private String password;

    @Size(min = 2, max = 100)
    private String name;

    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthday;

    @NotNull
    private String information;

    @Size(min = 2, max = 100)
    private String address;

    private MultipartFile image;
}
