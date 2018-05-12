package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Seat  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false)
    private Integer chairRow;

    @Column(nullable = false)
    @NotNull
    private Integer chairNumber;

    @Column(nullable = false)
    @NotNull
    private boolean available;

    @ManyToOne
    //@JoinColumn(name = "hall_id")
    @JsonBackReference
    private Hall hall;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private Ticket ticket;

    public Seat(){this.available = true;}

    public Seat(Long id, Integer chairRow, @NotNull Integer chairNumber, Hall hall) {
        this.id = id;
        this.chairRow = chairRow;
        this.chairNumber = chairNumber;
        this.hall = hall;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChairRow() {
        return chairRow;
    }

    public void setChairRow(Integer chairRow) {
        this.chairRow = chairRow;
    }

    public Integer getChairNumber() {
        return chairNumber;
    }

    public void setChairNumber(Integer chairNumber) {
        this.chairNumber = chairNumber;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
