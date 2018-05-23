package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;
import com.cinemas_theaters.cinemas_theaters.serializer.CustomFriendSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friendship", uniqueConstraints = @UniqueConstraint(columnNames = {"first_user_id", "second_user_id"}))
public class Friendship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JsonSerialize(using = CustomFriendSerializer.class)
    private RegisteredUser firstUser;

    @ManyToOne
    @JsonSerialize(using = CustomFriendSerializer.class)
    private RegisteredUser secondUser;

    @Column
    private FriendshipStatus status;

    public Friendship() { }

    public Friendship(RegisteredUser firstUser, RegisteredUser secondUser, FriendshipStatus status) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisteredUser getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(RegisteredUser firstUser) {
        this.firstUser = firstUser;
    }

    public RegisteredUser getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(RegisteredUser secondUser) {
        this.secondUser = secondUser;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
