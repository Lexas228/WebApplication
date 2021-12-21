package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement
public abstract class BasicPersonDto {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    protected String login;

    @Size(min = 2, max = 100)
    protected String name;

    @Size(min = 2, max = 100)
    protected String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    protected Date birthday;

    @NotNull
    protected String information;

    @Size(min = 2, max = 100)
    protected String address;
}
