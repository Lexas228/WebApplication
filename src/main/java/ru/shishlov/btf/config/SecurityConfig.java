package ru.shishlov.btf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shishlov.btf.services.PeopleService;
@ComponentScan({"ru.shishlov.btf"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PeopleService peopleService;
    @Autowired
    public void setPeopleService(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.formLogin();
        // http builder configurations for authorize requests and form login (see below)
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(peopleService);
        return authenticationProvider;
    }

}
