package com.cinemas_theaters.cinemas_theaters.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuickTicket extends Ticket {
    @Column
    private Integer discount;

    public QuickTicket(Integer discount) {
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, Integer discount) {
        super(id, seat, projection);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, Theatre theatre, Reservation reservation, Integer discount) {
        super(id, seat, projection, theatre, reservation);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, Theatre theatre, Reservation reservation, Integer discount) {
        super(seat, projection, theatre, reservation);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, Theatre theatre, Integer discount) {
        super(seat, projection, theatre);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Projection projection, Theatre theatre, Integer discount) {
        super(id, seat, projection, theatre);
        this.discount = discount;
    }

    public QuickTicket(Seat seat, Projection projection, Integer discount) {
        super(seat, projection);
        this.discount = discount;
    }

    public QuickTicket(Long id, Seat seat, Integer discount) {
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
