package ru.shishlov.btf.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DialogDto {
    private Long id;
    private List<String> peopleLogins;
    private List<MessageDto> messages;
}
