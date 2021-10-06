package ru.shishlov.btf.model;


import org.springframework.format.annotation.DateTimeFormat;
import ru.shishlov.btf.validators.LoginConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


public class Person {
    @Size(min = 2, max = 100)
    private String name;
    @Size(min = 2, max = 100)
    private String surname;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    @LoginConstraint
    @Size(min = 2, max = 100)
    private String login;
    @Size(min = 2, max = 100)
    private String password;
    private String information;
    @Size(min = 2, max = 100)
    private String address;

    public Person(String name, String surname, Date birthday, String login, String password, String information, String address){
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.information = information;
        this.address = address;
    }

    public Person(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
