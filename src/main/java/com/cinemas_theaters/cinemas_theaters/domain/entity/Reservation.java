package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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


    @Column(nullable = false)
    private String type;


    //@Column(nullable = false)
    //private String issueDate;

    //@Column(nullable = false)
    //private Double price;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private RegisteredUser buyer;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @ManyToOne(fetch = FetchType.LAZY)
    private Projection projection;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invitation> invitations;



    public Reservation() {
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets, Projection projection, List<Invitation> invitations, String type) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
        this.projection = projection;
        this.invitations = invitations;
        this.type = type;
    }

    public Reservation(Long id, String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets, String type) {
        this.id = id;
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
        this.type = type;
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer, String type) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.type = type;
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets, String type) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
        this.type = type;
    }

    public Reservation(Long id, String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets, Projection projection, String type) {
        this.id = id;
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
        this.projection = projection;
        this.type = type;
    }

    public Reservation(String showTitle, String projectionDate, RegisteredUser buyer, List<Ticket> tickets, Projection projection, String type) {
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.buyer = buyer;
        this.tickets = tickets;
        this.projection = projection;
        this.type =type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}
