package ru.shishlov.btf.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class MessageDto {
    private Long dialogId;
    private String text;
    private String loginFrom;
    private Timestamp date;
}
