package ru.shishlov.btf.components;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

@Component
public class PersonParser {
    private final XmlMapper xmlMapper;

    @Autowired
    public PersonParser(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public PersonDto toPersonFromXml(MultipartFile file) throws IOException {
        return xmlMapper.readValue(file.getInputStream(), PersonDto.class);
    }

    public void toXmlFromPersonInfo(PersonInformationDto person, OutputStream stream){
        try {
            xmlMapper.writeValue(stream, person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toXmlFromPerson(PersonDto person, OutputStream stream){
        try {
            xmlMapper.writeValue(stream, person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
