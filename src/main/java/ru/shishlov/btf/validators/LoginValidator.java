package ru.shishlov.btf.validators;


import org.springframework.beans.factory.annotation.Autowired;
import ru.shishlov.btf.dao.PeopleDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements
        ConstraintValidator<LoginConstraint, String> {
    private final PeopleDao peopleResource;
    @Autowired
    public LoginValidator(PeopleDao peopleResource) {
        this.peopleResource = peopleResource;
    }

    @Override
    public void initialize(LoginConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return peopleResource.findByLogin(login) == null;
    }
}
