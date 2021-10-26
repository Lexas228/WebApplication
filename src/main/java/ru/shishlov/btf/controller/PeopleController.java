package ru.shishlov.btf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shishlov.btf.components.PersonParser;
import ru.shishlov.btf.dto.PasswordDto;
import ru.shishlov.btf.dto.PersonDto;
import ru.shishlov.btf.dto.PersonInformationDto;
import ru.shishlov.btf.services.PeopleService;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonParser personParser;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonParser personParser){
        this.peopleService = peopleService;
        this.personParser = personParser;
    }

    @GetMapping("/home")
    public String homeRedirection(Principal principal){
        return "redirect:"+ principal.getName();
    }

    @GetMapping()
    public String all(Model model){
        model.addAttribute("people", peopleService.getAll());
        return "people/all";
    }
    @PreAuthorize("#login.equals(principal.username)")
    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("personInformation", peopleService.findByLogin(login).getPersonInformation());
        model.addAttribute("login", login);
        return "people/edit";
    }
    @PreAuthorize("#login.equals(principal.username)")
    @PostMapping("/{login}/edit")
    public String update(@ModelAttribute("person") @Valid PersonInformationDto personInformation,
                         BindingResult bindingResult,
                         @PathVariable("login") String login){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.updateInfo(personInformation, login);
        return "redirect:/people/home";
    }

    @PreAuthorize("#login.equals(principal.username)")
    @GetMapping("/{login}/resetPassword")
    public String changingPassword(@PathVariable("login") String login,
                                   @ModelAttribute("password") PasswordDto password, Model model) {

        model.addAttribute("login", login);
        return "people/resetPassword";
    }

    @PreAuthorize("#login.equals(principal.username)")
    @PostMapping("/{login}/resetPassword")
    public String changePassword(@ModelAttribute("password") @Valid PasswordDto password,
                                 BindingResult result, @PathVariable String login) {
        if(!peopleService.isCorrectPassword(login, password.getUserOldPassword())){
            result.rejectValue("userOldPassword", "userOldPassword", "Old password is wrong");
        }
        if(result.hasErrors()){
            return "people/resetPassword";
        }
        peopleService.changePassword(login, password.getNewPassword());
        return "redirect:/people/home";
    }

    @GetMapping("/{login}")
    public String showInfo(@PathVariable String login, Model model, Principal principal){
        model.addAttribute("personInformation", peopleService.findByLogin(login).getPersonInformation());
        model.addAttribute("userName", principal.getName());
        model.addAttribute("login", login);
        return "people/information";
    }

    @PostMapping( path = "/new",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String create(@ModelAttribute("person") @Valid PersonDto person, BindingResult bindingResult){
        if(!peopleService.isAvailableLogin(person.getLogin())){
            bindingResult.rejectValue("login", "login", "This login is busy");
        }
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people/"+person.getLogin();
    }

    @PostMapping(path = "/new/xml",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createWithXml(@ModelAttribute("file") MultipartFile file){
        System.out.println("here");
        PersonDto personDto = personParser.toPersonFromXml(file);
        System.out.println(personDto.getLogin());
        return "redirect:/people/new";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") PersonDto person){
        person.setPersonInformation(new PersonInformationDto());
        return "/people/new";
    }

    @PreAuthorize("#login.equals(principal.username)")
    @PostMapping("/{login}/delete")
    public String delete(@PathVariable("login") String login) {
        peopleService.delete(login);
        return "redirect:/logout";
    }
}
