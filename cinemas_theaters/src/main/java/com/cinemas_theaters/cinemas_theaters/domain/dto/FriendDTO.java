package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;

public class FriendDTO {

    String username;
    String fistname;
    String lastname;
    FriendshipStatus status;

    public FriendDTO() { }

    public FriendDTO(String username, String fistname, String lastname, FriendshipStatus status) {
        this.username = username;
        this.fistname = fistname;
        this.lastname = lastname;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFistname() {
        return fistname;
    }

    public void setFistname(String fistname) {
        this.fistname = fistname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
