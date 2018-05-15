package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import java.io.Serializable;


public class UserLoginDTO implements Serializable {
    private String username;

    private String password;

    private UserType type;

    private Long id;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String username, String password, UserType type, Long id) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.id = id;

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
