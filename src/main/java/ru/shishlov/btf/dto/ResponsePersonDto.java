package ru.shishlov.btf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.util.file.ConfigurationSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
@XmlRootElement
public class ResponsePersonDto extends BasicPersonDto{
    private String login;

    private byte[] image;

    private Date lastAction;
}
