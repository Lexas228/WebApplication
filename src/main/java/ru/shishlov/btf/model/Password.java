package ru.shishlov.btf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shishlov.btf.validators.FieldMatch;
import ru.shishlov.btf.validators.PasswordConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
public class Password {
    @NotEmpty
    private String login;
    @NotEmpty
    @PasswordConstraint
    private String oldPassword;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirmNewPassword;
}
