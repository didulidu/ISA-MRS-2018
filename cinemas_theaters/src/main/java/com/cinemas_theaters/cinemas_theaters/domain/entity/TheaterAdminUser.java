package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.serializer.CustomFriendshipSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class TheaterAdminUser extends User implements Serializable {

    @Column(nullable = false)
    @NotNull
    private boolean registrationConfirmed;


    @OneToMany(mappedBy = "theaterAdminUser",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Theatre> theatres;

    public TheaterAdminUser(){
        this.theatres = new ArrayList<Theatre>();
    }


    public TheaterAdminUser(@Email String email, @NotNull boolean registrationConfirmed,  List<Theatre> theatres) {
        this.registrationConfirmed = registrationConfirmed;
        this.theatres = theatres;
    }

    public TheaterAdminUser(String name, String lastname, String username, String password, UserType type, @Email String email, @NotNull boolean registrationConfirmed, List<Theatre> theatres) {
        super(name, lastname, username, password, type, email);
        this.registrationConfirmed = registrationConfirmed;
        this.theatres = theatres;
    }

    public TheaterAdminUser(String username, UserType type, @Email String email, @NotNull boolean registrationConfirmed, List<Theatre> theatres) {
        super(username, type, email);
        this.registrationConfirmed = registrationConfirmed;
        this.theatres = theatres;
    }

    public TheaterAdminUser(String username, String name, UserType type, @Email String email,@NotNull boolean registrationConfirmed,  List<Theatre> theatres) {
        super(username, name, type, email);
        this.registrationConfirmed = registrationConfirmed;
        this.theatres = theatres;
    }


    public boolean isRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public void setRegistrationConfirmed(boolean registrationConfirmed) {
        this.registrationConfirmed = registrationConfirmed;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(List<Theatre> theatres) {
        this.theatres = theatres;
    }
}
