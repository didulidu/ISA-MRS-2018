package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUserDTO {
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String address;
    private String telephoneNumber;
    private UserType type;
    private List<FriendDTO> friendships;

    public RegisteredUserDTO() {
        this.friendships = new ArrayList<>();
    }

    /*public RegisteredUserDTO(String name, String lastname, String username, String email, HashMap<RegisteredUser, Friendship> friendships) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.type = UserType.RegisteredUser;
        this.friendships = friendships;
    }*/

    public RegisteredUserDTO(String name, String lastname, String username, String email, String address, String telephoneNumber, UserType type, List<FriendDTO> friendships) {
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

    public List<FriendDTO> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<FriendDTO> friendships) {
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
