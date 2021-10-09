package ru.shishlov.btf.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.shishlov.btf.model.Password;
import ru.shishlov.btf.model.Person;
import ru.shishlov.btf.services.PeopleService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements
        ConstraintValidator<PasswordConstraint, String> {
    private final PeopleService peopleService;

    @Autowired
    public PasswordValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Person pers = peopleService.findByLogin(login);
        return peopleService.isCorrectPassword(pers, password);
    }
}