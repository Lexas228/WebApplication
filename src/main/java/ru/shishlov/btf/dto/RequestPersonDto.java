package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.validators.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@XmlRootElement
public class RequestPersonDto extends BasicPersonDto{

    @Size(min = 2, max = 100)
    private String password;

    private String confirmPassword;

}
