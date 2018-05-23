package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(nullable = false)
    private String showTitle;

    @Column(nullable = false)
    private String projectionDate;

    //@Column(nullable = false)
    //private String issueDate;

    //@Column(nullable = false)
    //private Double price;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RegisteredUser buyer;

    @OneToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @JsonBackReference(value = "projection")
    @ManyToOne
    private Projection projection;

    public Ticket(){}

    public Ticket(Long id, String showTitle, String projectionDate, RegisteredUser buyer, Seat seat, Projection projection) {
        this.id = id;
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.seat = seat;
        this.projection = projection;
    }

    public Ticket(String showTitle, String projectionDate, RegisteredUser buyer, Seat seat, Projection projection) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.seat = seat;
        this.projection = projection;
    }

    public Ticket(Long id, String showTitle, String projectionDate, RegisteredUser buyer, Seat seat) {
        this.id = id;
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(String projectionDate) {
        this.projectionDate = projectionDate;
    }

    public RegisteredUser getBuyer() {
        return buyer;
    }

    public void setBuyer(RegisteredUser buyer) {
        this.buyer = buyer;
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
}
