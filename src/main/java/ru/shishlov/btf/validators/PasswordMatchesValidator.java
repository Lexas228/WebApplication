package ru.shishlov.btf.validators;

import ru.shishlov.btf.model.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        Person user = (Person) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
