package ru.shishlov.btf.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;
import ru.shishlov.btf.validators.LoginConstraint;
import ru.shishlov.btf.validators.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class Person {
    @Size(min = 2, max = 100)
    private String name;
    @Size(min = 2, max = 100)
    private String surname;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    @LoginConstraint
    @Size(min = 2, max = 100)
    private String login;
    @Size(min = 2, max = 100)
    private String password;
    private String confirmPassword;
    private String information;
    @Size(min = 2, max = 100)
    private String address;

}
