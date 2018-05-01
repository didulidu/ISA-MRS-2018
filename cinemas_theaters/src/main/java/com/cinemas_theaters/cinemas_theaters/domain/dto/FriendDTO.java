package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;

public class FriendDTO {
    private String name;
    private String lastname;
    private String username;
    private FriendshipStatus status;

    public FriendDTO() { }

    public FriendDTO(String name, String lastname, String username, FriendshipStatus status) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.status = status;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
