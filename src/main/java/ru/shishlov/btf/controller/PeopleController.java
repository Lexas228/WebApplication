package ru.shishlov.btf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.model.Password;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.model.PersonInformation;
import ru.shishlov.btf.services.PeopleInformationService;
import ru.shishlov.btf.services.PeopleService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PeopleInformationService peopleInformationService;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleInformationService peopleInformationService){
        this.peopleService = peopleService;
        this.peopleInformationService = peopleInformationService;
    }

    @GetMapping("/home")
    public String homeRedirection(Principal principal){
        return "redirect:"+ principal.getName();
    }

    @GetMapping()
    public String all(Model model){
        model.addAttribute("people", peopleInformationService.getAll());
        return "people/all";
    }
    @PreAuthorize("#login.equals(principal.username)")
    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("personInformation", peopleInformationService.findByLogin(login));
        return "people/edit";
    }
    @PostMapping("/{login}/edit")
    public String update(@ModelAttribute("person") @Valid PersonInformation personInformation,
                         BindingResult bindingResult,
                         @PathVariable("login") String login){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleInformationService.update(personInformation, login);
        return "redirect:/people/home";
    }

    @PreAuthorize("#login.equals(principal.username)")
    @GetMapping("/{login}/resetPassword")
    public String changingPassword(Model model, @PathVariable("login") String login) {
        Password p = new Password();
        p.setLogin(login);
        model.addAttribute("password", p);
        return "people/resetPassword";
    }

    @PreAuthorize("#password.login.equals(principal.username)")
    @PostMapping("/{login}/resetPassword")
    public String changePassword(@ModelAttribute("password") @Valid Password password, BindingResult result) {
        if(result.hasErrors()){
            return "people/resetPassword";
        }
        peopleService.changePassword(password.getLogin(), password.getNewPassword());
        return "redirect:/people/home";
    }

    @GetMapping("/{login}")
    public String showInfo(@PathVariable String login, Model model, Principal principal){
        model.addAttribute("personInformation", peopleInformationService.findByLogin(login));
        model.addAttribute("userName", principal.getName());
        return "people/information";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
    @ModelAttribute("personInformation") PersonInformation personInformation, BindingResult bindingInfoResult){
        if(bindingResult.hasErrors() || bindingInfoResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        peopleInformationService.save(personInformation);
        return "redirect:/people/"+person.getLogin();
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person,
                            @ModelAttribute("personInformation") PersonInformation personInformation){
        return "/people/new";
    }



    @PreAuthorize("#login.equals(principal.username)")
    @PostMapping("/{login}/delete")
    public String delete(@PathVariable("login") String login) {
        peopleService.delete(login);
        peopleInformationService.delete(login);
        return "redirect:/logout";
    }
}
