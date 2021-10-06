package ru.shishlov.btf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.dao.PeopleDao;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDao peopleResource;

    public PeopleController(PeopleDao peopleResource) {
        this.peopleResource = peopleResource;
    }



    @GetMapping()
    public String home(Model model){
        model.addAttribute("people", peopleResource.getAll());
        return "people/home";
    }

    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("person", peopleResource.findByLogin(login));
        return "people/edit";
    }

    @GetMapping("/{login}")
    public String showInfo(@PathVariable String login, Model model){
        model.addAttribute("person", peopleResource.findByLogin(login));
        return "people/information";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleResource.add(person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @PostMapping("/{login}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("login") String login){
        if(bindingResult.hasErrors() && (bindingResult.getErrorCount() > 1 || !bindingResult.hasFieldErrors("login"))){
            return "people/new";
        }

        peopleResource.update(login, person);
        return "redirect:/people";
    }

    @PostMapping("/{login}/delete")
    public String delete(@PathVariable("login") String id) {
        peopleResource.delete(id);
        return "redirect:/people";
    }


}
