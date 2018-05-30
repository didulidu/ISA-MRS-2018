package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Reservation   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public Reservation() {
    }



    public Reservation(Long id, String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets) {
        this.id = id;
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
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
}
