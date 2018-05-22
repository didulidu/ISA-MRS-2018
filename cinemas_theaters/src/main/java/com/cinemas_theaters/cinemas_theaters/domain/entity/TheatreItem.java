package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "THEATRE_ITEM")
public class TheatreItem extends Item {

    private Double price;
    private Long quantity;
    private Theatre theatre;

    public TheatreItem(){}

    public TheatreItem(String name, String description, Double price, Long quantity, Theatre theatre, Set categories) {

        super(name, description, categories);
        this.price = price;
        this.theatre = theatre;
        this.quantity = quantity;
    }


    @Column(name="PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name="QUANTITY")
    public Long getQuantity(){
        return quantity;
    }

    public void setQuantity(Long quantity){
        this.quantity = quantity;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
}
