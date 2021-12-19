package ru.shishlov.btf.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@Data
@XmlRootElement
public class DialogDto {
    private Long id;
    @NotNull
    @NotBlank
    private String withWhom;
}
