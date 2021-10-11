package ru.shishlov.btf.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shishlov.btf.validators.FieldMatch;
import ru.shishlov.btf.validators.LoginConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class Person {
    private long id;
    @LoginConstraint
    @Size(min = 2, max = 100)
    private String login;
    @Size(min = 2, max = 100)
    private String password;
    private String confirmPassword;
    @NotNull
    private PersonInformation personInformation;
}
