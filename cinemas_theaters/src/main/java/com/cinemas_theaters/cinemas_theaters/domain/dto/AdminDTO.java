package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

public class AdminDTO {

    private UserType type;
    private String email;

    public AdminDTO(){}

    public AdminDTO(UserType type, String email){
        this.type = type;
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
