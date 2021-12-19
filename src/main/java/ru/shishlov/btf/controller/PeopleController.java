package ru.shishlov.btf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shishlov.btf.components.security.ValidLogin;
import ru.shishlov.btf.dto.PasswordDto;
import ru.shishlov.btf.dto.RequestPersonDto;
import ru.shishlov.btf.dto.ResponsePersonDto;
import ru.shishlov.btf.services.PeopleService;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping("/people")
@CrossOrigin
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public Collection<ResponsePersonDto> all() {
        return peopleService.getAll();
    }

    @PreAuthorize("@validLogin.check(#login, authentication)")
    @PutMapping("/{login}")
    public ResponseEntity<String> edit(@RequestBody @Valid RequestPersonDto person,
                                       @PathVariable(name = "login") String login) {
        peopleService.updateInfo(person, login);
        return ResponseEntity.ok("Was updated");
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid RequestPersonDto persondto) {
        if(!peopleService.isAvailableLogin(persondto.getLogin())){
            return ResponseEntity.badRequest().body("Login is busy");
        }
        peopleService.save(persondto);
        return ResponseEntity.ok("User was created");
    }

    @PreAuthorize("@validLogin.check(#login, authentication)")
    @PutMapping("/password/{login}")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDto passwordDto, @PathVariable String login) {
        if (peopleService.isCorrectPassword(login, passwordDto.getUserOldPassword())) {
            peopleService.changePassword(login, passwordDto.getNewPassword());
            return ResponseEntity.ok("Password was changed");
        }
        return ResponseEntity.badRequest().body("Old password is incorrect");
    }


    @GetMapping("/{login}")
    public ResponsePersonDto getPerson(@PathVariable String login) {
        return peopleService.findByLogin(login);
    }

    @PreAuthorize("@validLogin.check(#login, authentication)")
    @DeleteMapping("/{login}")
    public ResponseEntity<String> deletePerson(@PathVariable("login") String login) {
        peopleService.delete(login);
        return ResponseEntity.ok("Was deleted");
    }
}