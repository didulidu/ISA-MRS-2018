package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

public class TheaterAdminUserDTO {
    private String name;
    private String lastname;
    private String username;
    private String email;
    private UserType type;


    public TheaterAdminUserDTO(String name, String lastname, String username, String emails) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.type = UserType.RegisteredUser;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}
