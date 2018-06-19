package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RegisteredUser extends User implements Serializable {

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
    private List<Friendship> friendships;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private  List<Reservation> reservations;

    @OneToMany(mappedBy = "invitedUser", fetch = FetchType.LAZY)
    private List<Invitation> invitations;

    public RegisteredUser() {
        super();
        this.avatarUrl = "";
        this.registrationConfirmed = false;
        this.friendships = new ArrayList<>();
    }

    public RegisteredUser(String username, String password, UserType type, String name, String lastname, String email) {
        super(name, lastname, username, password, type, email);
        this.avatarUrl = "";
        this.registrationConfirmed = false;
        this.friendships = new ArrayList<>();
    }

    public RegisteredUser(@Email String email, @NotNull String avatarUrl, @NotNull boolean registrationConfirmed, @NotNull String address, @NotNull String telephoneNumber, List<Friendship> friendships, List<Ticket> tickets, List<Reservation> reservations) {
        super(email);
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

    public List<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(List<Friendship> friendships) {
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

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}
