package ru.shishlov.btf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shishlov.btf.validators.FieldMatch;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
public class Password {
    @NotEmpty
    private String userOldPassword;
    @NotEmpty
    @Size(min = 2, max = 100)
    private String newPassword;
    @NotEmpty
    private String confirmNewPassword;
}
