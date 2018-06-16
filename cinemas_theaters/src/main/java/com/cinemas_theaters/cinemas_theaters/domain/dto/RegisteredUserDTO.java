package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.cinemas_theaters.cinemas_theaters.serializer.CustomFriendshipSerializer;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import java.util.HashMap;
import java.util.Map;

public class RegisteredUserDTO {
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String address;
    private String telephoneNumber;
    private UserType type;
    @JsonSerialize(using = CustomFriendshipSerializer.class)
    private Map<RegisteredUser, Friendship> friendships;

    public RegisteredUserDTO() {
        this.friendships = new HashMap<>();
    }

    /*public RegisteredUserDTO(String name, String lastname, String username, String email, HashMap<RegisteredUser, Friendship> friendships) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.type = UserType.RegisteredUser;
        this.friendships = friendships;
    }*/

    public RegisteredUserDTO(String name, String lastname, String username, String email, String address, String telephoneNumber, UserType type, Map<RegisteredUser, Friendship> friendships) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.type = type;
        this.friendships = friendships;
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

    public Map<RegisteredUser, Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(Map<RegisteredUser, Friendship> friendships) {
        this.friendships = friendships;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
