package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @Column
    @Size(min = 2, max = 30)
    private String lastname;

    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 20)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    public User() { }

    public User(String name, String lastname, String username, String password, UserType type) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(String username, UserType type){
        this.username = username;
        this.type = type;
    }

    public User(String username, String name, UserType type){
        this.name = name;
        this.username = username;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
