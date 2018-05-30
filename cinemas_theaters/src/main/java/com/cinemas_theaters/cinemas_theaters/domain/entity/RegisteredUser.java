package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;

import com.cinemas_theaters.cinemas_theaters.serializer.CustomFriendshipSerializer;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class RegisteredUser extends User implements Serializable {

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    private String avatarUrl;

    @Column(nullable = false)
    @NotNull
    private boolean registrationConfirmed;

    @Column(nullable = false)
    @NotNull
    private String address;

    @Column(nullable = false)
    @NotNull
    private String telephoneNumber;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL)
    @JsonSerialize(using = CustomFriendshipSerializer.class)
    private Map<RegisteredUser, Friendship> friendships;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private  List<Reservation> reservations;


    //@OneToMany(mappedBy = "invited", fetch = FetchType.LAZY)
    //@JsonSerialize(using = CustomInviteUserSerializer.class)
    //private List<Invite> invites;

    public RegisteredUser() {
        super();
        this.avatarUrl = "";
        this.registrationConfirmed = false;
        this.friendships = new HashMap<>();
        //this.invites = new ArrayList<>();
        //this.personalReservations = new ArrayList<>();
    }

    public RegisteredUser(String username, String password, UserType type, String name, String lastname, String email) {
        super(name, lastname, username, password, type);
        this.avatarUrl = "";
        this.email = email;
        this.registrationConfirmed = false;
        this.friendships = new HashMap<>();
        //this.invites = new ArrayList<>();
        //this.personalReservations = new ArrayList<>();
    }

    public RegisteredUser(@Email String email, @NotNull String avatarUrl, @NotNull boolean registrationConfirmed, @NotNull String address, @NotNull String telephoneNumber, Map<RegisteredUser, Friendship> friendships, List<Ticket> tickets, List<Reservation> reservations) {
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.registrationConfirmed = registrationConfirmed;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.friendships = friendships;
        this.reservations = reservations;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean getRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public void setRegistrationConfirmed(boolean registrationConfirmed) {
        this.registrationConfirmed = registrationConfirmed;
    }

    public boolean isRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
