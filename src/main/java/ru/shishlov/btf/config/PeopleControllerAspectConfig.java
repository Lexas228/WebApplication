package ru.shishlov.btf.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.shishlov.btf.services.PeopleService;

import java.security.Principal;

@Configuration
@Aspect
public class PeopleControllerAspectConfig {

    private PeopleService peopleService;

    @Autowired @Lazy
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @After("execution(* ru.shishlov.btf.controller.*.*(..)))")
    public void updateLastAction(){
        Principal pre = SecurityContextHolder.getContext().getAuthentication();
        if(pre != null)
        peopleService.updateLastAction(pre.getName());
    }
}
