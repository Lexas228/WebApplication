package ru.shishlov.btf.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PersonInformationDto {

    @Size(min = 2, max = 100)
    private String name;

    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @NotNull
    private String information;

    @Size(min = 2, max = 100)
    private String address;
}