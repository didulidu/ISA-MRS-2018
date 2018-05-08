package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`hall`")
public class Hall  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "hall",fetch = FetchType.LAZY)
    private List<Seat> seats;

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "shows_halls",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id")
    )
    //tabela sa filmovima/predstavama i njihovim salama (salama i njihovim filmovima/predstavama)
    private List<Show> shows;*/

    @OneToMany(mappedBy = "hall",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    //@Column(insertable=false)
    private List<Projection> projections;

    @ManyToOne
    //@JoinColumn(name = "theatre_id")
    @JsonBackReference
    private Theatre theatre;

    public Hall() {
    }

    public Hall(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hall(String name, Long id, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public Hall(Long id, String name, List<Projection> projections) {
        this.id = id;
        this.name = name;
        this.projections = projections;
    }

    public Hall(Long id, String name, List<Seat> seats, List<Projection> projections) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.projections = projections;
    }

    public Hall(Long id, String name, List<Seat> seats, List<Projection> projections, Theatre theatre) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.projections = projections;
        this.theatre = theatre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
}
