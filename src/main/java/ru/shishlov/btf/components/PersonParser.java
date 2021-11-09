package ru.shishlov.btf.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class for making xml or json from person and vice versa
 */
@Component
public class PersonParser {

    public PersonDto toPersonFromXml(MultipartFile file) throws IOException {
        return toPersonFromFile(file, new XmlMapper());
    }

    public PersonDto toPersonFromJson(MultipartFile file) throws IOException {
        return toPersonFromFile(file, new ObjectMapper());
    }

    public void toXmlFromPersonInfo(PersonInformationDto person, OutputStream stream) throws IOException {
        toFileFromPersonInfo(person, stream, new XmlMapper());
    }

    public void toJsonFromPersonInfo(PersonInformationDto person, OutputStream stream) throws IOException {
        toFileFromPersonInfo(person, stream, new ObjectMapper());
    }


    public void toXmlFromPerson(PersonDto person, OutputStream stream) throws IOException {
        toFileFromPerson(person, stream, new XmlMapper());
    }

    public void toJsonFromPerson(PersonDto person, OutputStream stream) throws IOException {
        toFileFromPerson(person, stream, new ObjectMapper());
    }

    private PersonDto toPersonFromFile(MultipartFile file, ObjectMapper mapper) throws IOException {
        return mapper.readValue(file.getInputStream(), PersonDto.class);
    }

    private void toFileFromPerson(PersonDto person, OutputStream stream, ObjectMapper mapper) throws IOException {
        mapper.writeValue(stream, person);
    }

    private void toFileFromPersonInfo(PersonInformationDto info, OutputStream stream, ObjectMapper mapper) throws IOException {
        mapper.writeValue(stream, info);
    }

}
