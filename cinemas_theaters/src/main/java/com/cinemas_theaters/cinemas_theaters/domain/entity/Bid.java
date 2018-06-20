package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.BidStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "BID")
public class Bid {

    private Long id;

    private User user;

    private Item item;

    private Double bid;

    private BidStatus status;

    private Boolean active;


    private Long version;

    public Bid(){}

    public Bid( User user, Item item, Double bid, BidStatus status) {

        this.user = user;
        this.item = item;
        this.bid = bid;
        this.status = status;
        this.active = true;
    }

    @Id
    @Column(name = "BID_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column
    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    @Column
    public BidStatus getStatus(){return this.status;}

    public void setStatus(BidStatus status){this.status = status;}

    @Column
    public Boolean getActive(){return this.active;}

    public void setActive(Boolean active){this.active = active;}

    @Version
    @Column(nullable = false)
    public Long getVersion(){return this.version;}
    public void setVersion(Long version){this.version = version;}
}
