package com.cinemas_theaters.cinemas_theaters.domain.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.Constraint;
import java.time.Duration;

@Entity
@Table(name = "USER_ITEM")
public class UserItem extends Item {

    private RegisteredUser user;
    private Duration duration;

    public UserItem(){}

    public UserItem(String name, String description, RegisteredUser user, Duration duration) {

        super(name, description);
        this.user = user;
        this.duration = duration;
    }

    public UserItem(String name, String description, Duration duration){
        super(name, description);
        this.duration = duration;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    @Column(name = "DURATION")
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
