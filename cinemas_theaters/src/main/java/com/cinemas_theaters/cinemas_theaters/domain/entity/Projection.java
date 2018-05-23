package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`projection`")
public class Projection  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projection_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String date;

    //@Column(nullable = false)
    //@JoinColumn(name = "show_id")
    //@NotNull
    @ManyToOne
    //@JsonIgnore
    private Show show;

    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "reserved_seats" )
    @Column(name = "reserved_seats")
    private List<String> reservedSeats;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "hall_hall_id")
    private Hall hall;

    @Column(nullable = false)
    @NotNull
    private Integer price;

    @Column(nullable = false)
    private Boolean exist;


    public Projection(){reservedSeats = new ArrayList<>();
    }



    public Projection(@NotNull String date, @NotNull Show show, Hall hall, @NotNull Integer price) {
        this.date = date;
        this.show = show;
        this.hall = hall;
        this.price = price;
        reservedSeats = new ArrayList<>();
        this.exist = true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }


}

