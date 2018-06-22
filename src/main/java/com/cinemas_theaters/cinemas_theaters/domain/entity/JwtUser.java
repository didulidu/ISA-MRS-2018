package com.cinemas_theaters.cinemas_theaters.domain.entity;

public class JwtUser {

    private String username;

    public JwtUser() { }

    public JwtUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}