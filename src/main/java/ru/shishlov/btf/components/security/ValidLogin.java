package ru.shishlov.btf.components.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidLogin {

    public boolean check(String login, Authentication authentication) {
        if(login == null || authentication==null)
            return false;
        return login.equals(authentication.getName());
    }
}
