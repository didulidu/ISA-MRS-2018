package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ticket  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private Long id;

    //@Column(nullable = false)
    //private String issueDate;

    //@Column(nullable = false)
    //private Double price;

    @OneToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @JsonBackReference(value = "projection")
    @ManyToOne
    @JsonIgnore
    private Projection projection;

    @ManyToOne
    private Theatre theatre;

    @ManyToOne
    @JsonIgnore
    private Reservation reservation;

    @Version
    @Column(nullable = false)
    private long version;

    public Ticket(){}

    public Ticket(Long id, Seat seat, Projection projection) {
        this.id = id;
        this.seat = seat;
        this.projection = projection;
    }

    public Ticket(Long id, Seat seat, Projection projection, Theatre theatre, Reservation reservation) {
        this.id = id;
        this.seat = seat;
        this.projection = projection;
        this.theatre = theatre;
        this.reservation = reservation;
    }

    public Ticket(Seat seat, Projection projection, Theatre theatre, Reservation reservation) {
        this.seat = seat;
        this.projection = projection;
        this.theatre = theatre;
        this.reservation = reservation;
    }

    public Ticket(Seat seat, Projection projection, Theatre theatre) {
        this.seat = seat;
        this.projection = projection;
        this.theatre = theatre;
    }

    public Ticket(Long id, Seat seat, Projection projection, Theatre theatre) {
        this.id = id;
        this.seat = seat;
        this.projection = projection;
        this.theatre = theatre;
    }

    public Ticket(Seat seat, Projection projection) {
        this.seat = seat;
        this.projection = projection;
    }

    public Ticket(Long id, Seat seat) {
        this.id = id;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
