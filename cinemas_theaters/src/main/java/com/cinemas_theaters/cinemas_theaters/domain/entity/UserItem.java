package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.UserItemStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "USER_ITEM")
public class UserItem extends Item {

    private User user;
    private Duration duration;
    private UserItemStatus status;
    private Date dataTime;

    public UserItem(){}

    public UserItem(String name, String description, User user, Duration duration) {

        super(name, description);
        this.user = user;
        this.duration = duration;
        this.status = UserItemStatus.PENDING;
    }

    public UserItem(String name, String description, User user, Duration duration, String imagePath) {

        super(name, description, imagePath);
        this.user = user;
        this.duration = duration;
        this.status = UserItemStatus.PENDING;
    }

    public UserItem(String name, String description, Duration duration){
        super(name, description);
        this.duration = duration;
        this.status = UserItemStatus.PENDING;
    }

    public UserItem(String name, String description, Date dateTime, User user, String imagePath){
        super(name, description, imagePath);
        this.duration = duration;
        this.dataTime = dateTime;
        this.user = user;
        this.status = UserItemStatus.PENDING;
    }


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "DURATION")
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Column(name="STATUS")
    public UserItemStatus getStatus(){return this.status;}

    public void setStatus(UserItemStatus status){this.status = status;}

    @Column(name="DATE")
    public Date getDataTime(){return this.dataTime;}

    public void setDataTime(Date dateTime){this.dataTime = dateTime;}
}
