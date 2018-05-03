package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;

import com.cinemas_theaters.cinemas_theaters.serializer.CustomFriendshipSerializer;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

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
    private String town;

    @Column(nullable = false)
    @NotNull
    private String telephoneNumber;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL)
    @JsonSerialize(using = CustomFriendshipSerializer.class)
    private Map<RegisteredUser, Friendship> friendships;

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

    public String getTown() {
        return avatarUrl;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    //public List<Invite> getInvites() { return invites; }

    //public void setInvites(List<Invite> invites) {this.invites = invites;}

    //public List<Reservation> getPersonalReservations() {return personalReservations;}

    //public void setPersonalReservations(List<Reservation> personalReservations) { this.personalReservations = personalReservations; }

    public boolean getRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public void setRegistrationConfirmed(boolean registrationConfirmed) {
        this.registrationConfirmed = registrationConfirmed;
    }
}
