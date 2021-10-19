package ru.shishlov.btf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import ru.shishlov.btf.components.validators.FieldMatch;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
public class PasswordDto {
    @NotEmpty
    private String userOldPassword;
    @NotEmpty
    @Size(min = 2, max = 100)
    private String newPassword;
    @NotEmpty
    private String confirmNewPassword;
}
