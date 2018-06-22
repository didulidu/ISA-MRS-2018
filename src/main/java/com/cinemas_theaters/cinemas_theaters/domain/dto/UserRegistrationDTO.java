package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class UserRegistrationDTO {
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String repeatedPassword;
    private String email;
    private String telephoneNumber;
    private String address;

    public UserRegistrationDTO() { }

    public UserRegistrationDTO(String name, String lastname, String username, String password, String repeatedPassword, String email) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.email = email;
    }

    public UserRegistrationDTO(String name, String lastname, String username, String password, String repeatedPassword, String email, String telephoneNumber, String address) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
