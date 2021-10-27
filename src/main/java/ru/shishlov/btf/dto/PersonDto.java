package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.shishlov.btf.components.validators.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto {
    @Size(min = 2, max = 100)
    private String login;

    @Size(min = 2, max = 100)
    private String password;

    private String confirmPassword;

    @NotNull
    private PersonInformationDto personInformation;
}
