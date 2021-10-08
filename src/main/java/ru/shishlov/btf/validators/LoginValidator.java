package ru.shishlov.btf.validators;


import org.springframework.beans.factory.annotation.Autowired;
import ru.shishlov.btf.dao.PeopleDao;
import ru.shishlov.btf.services.PeopleService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements
        ConstraintValidator<LoginConstraint, String> {
    private final PeopleService peopleService;
    @Autowired
    public LoginValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public void initialize(LoginConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return peopleService.findByLogin(login) == null;
    }
}
