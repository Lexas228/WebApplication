package ru.shishlov.btf.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.PersonParser;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.services.PeopleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/people/new")
public class AuthorizationController {
    private final PeopleService peopleService;
    private final PersonParser personParser;

    public AuthorizationController(PeopleService peopleService, PersonParser personParser){
        this.peopleService = peopleService;
        this.personParser = personParser;
    }

    @GetMapping
    public String newPerson(@ModelAttribute("person") PersonDto person){
        person.setPersonInformation(new PersonInformationDto());
        return "/people/new";
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String create(@ModelAttribute("person") @Valid PersonDto person, BindingResult bindingResult, HttpServletRequest request){
        if(!peopleService.isAvailableLogin(person.getLogin())){
            bindingResult.rejectValue("login", "login", "This login is busy");
        }
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        saveAndTryToLogin(person, request);
        return "redirect:/people/"+person.getLogin();
    }

    @PostMapping(path = "/file/{type}",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createWithXml(@ModelAttribute("file") MultipartFile file, HttpServletRequest request,
                                @PathVariable("type") String type) {
        try {
            PersonDto personDto;
            if(type.equals("xml")) personDto = personParser.toPersonFromXml(file);
            else if(type.equals("json")) personDto = personParser.toPersonFromJson(file);
            else{
                return "redirect:/people/login";
            }
            if (!peopleService.isAvailableToSave(personDto)) return "redirect:/people/login";
            saveAndTryToLogin(personDto, request);
        } catch (IOException e) {
            return "redirect:/people/login";
        }
        return "redirect:/people/home";
    }

    @GetMapping("/file/{type}")
    public void getXmlExample(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
        PersonDto personDto = new PersonDto();
        personDto.setPersonInformation(new PersonInformationDto());
        response.setContentType("application/"+type);
        response.addHeader("Content-Disposition", "attachment; filename=example." + type);
        if(type.equals("xml"))
        personParser.toXmlFromPerson(personDto, response.getOutputStream());
        else personParser.toJsonFromPerson(personDto, response.getOutputStream());
        response.getOutputStream().flush();
    }

    private boolean saveAndTryToLogin(PersonDto person, HttpServletRequest request){
        String rawPass = person.getPassword();
        peopleService.save(person);
        try {
            request.login(person.getLogin(), rawPass);
        } catch (ServletException e) {
            return false;
        }
        return true;
    }

}
