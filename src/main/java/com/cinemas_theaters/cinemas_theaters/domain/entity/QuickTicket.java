package com.cinemas_theaters.cinemas_theaters.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class QuickTicket extends Ticket implements Serializable {



    @Column(nullable = false)
    @NotNull
    private Integer discount;


    public QuickTicket(){super();};

    public QuickTicket(@NotNull Integer discount) {
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, @NotNull Integer discount) {
        super(id, seat, projection);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, Theatre theatre, Reservation reservation, @NotNull Integer discount) {
        super(id, seat, projection, theatre, reservation);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, Theatre theatre, Reservation reservation, @NotNull Integer discount) {
        super(seat, projection, theatre, reservation);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, Theatre theatre, @NotNull Integer discount) {
        super(seat, projection, theatre);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, Theatre theatre, @NotNull Integer discount) {
        super(id, seat, projection, theatre);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, @NotNull Integer discount) {
        super(seat, projection);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, @NotNull Integer discount) {
        super(id, seat);
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
