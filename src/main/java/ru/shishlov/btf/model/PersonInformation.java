package ru.shishlov.btf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInformation {
    private long personId;
    private long id;
    @Size(min = 2, max = 100)
    private String name;
    @Size(min = 2, max = 100)
    private String surname;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private String information;
    @Size(min = 2, max = 100)
    private String address;
}
