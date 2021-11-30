package ru.shishlov.btf.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageDto {
    private String text;
    private String loginTo;
    private String loginFrom;
    private Timestamp date;
}
