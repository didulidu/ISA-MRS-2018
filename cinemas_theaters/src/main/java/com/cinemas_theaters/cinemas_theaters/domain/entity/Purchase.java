package com.cinemas_theaters.cinemas_theaters.domain.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PURCHASE")
public class Purchase {

    private Long id;
    private Item item;
    private User user;

    public Purchase(){}

    public Purchase(Item item){
        this.item = item;
    }

    public Purchase(Item item, User user){
        this.item = item;
        this.user = user;
    }


    @Id
    @Column(name = "PURCHASE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
