package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "OFFER")
public class Offer {

    private Long id;

    //private RegisteredUser user;

    private Item item;

    private Double bid;

    public Offer() {    }

    /*public Offer(RegisteredUser user, Item item) {
        this.user = user;
        this.item = item;
    }
*/
    public Offer(Item item, Double bid){
        this.item = item;
        this.bid = bid;
    }

    @Id
    @Column(name = "OFFER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

/*

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }
*/

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
    @NotNull
    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }
}
