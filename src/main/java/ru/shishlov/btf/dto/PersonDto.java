package ru.shishlov.btf.dto;

import lombok.Data;
import ru.shishlov.btf.components.validators.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PersonDto {
    @Size(min = 2, max = 100)
    private String login;

    @Size(min = 2, max = 100)
    private String password;

    private String confirmPassword;

    @NotNull
    private PersonInformationDto personInformation;
}
