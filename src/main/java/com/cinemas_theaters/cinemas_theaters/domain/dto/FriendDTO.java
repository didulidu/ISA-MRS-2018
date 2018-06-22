package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;

public class FriendDTO {

    String username;
    String name;
    String lastname;
    InvitationStatus status;

    public FriendDTO() { }

    public FriendDTO(String username, String name, String lastname, InvitationStatus status) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
