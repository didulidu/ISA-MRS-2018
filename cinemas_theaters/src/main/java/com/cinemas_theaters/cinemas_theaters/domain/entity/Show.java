package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Show  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String title;

    @Column(nullable = false)
    @NotNull
    private Integer duration;

    @Column(nullable = false)
    @NotNull
    private Integer price;

    @Column(nullable = false)
    @NotNull
    private Double averageRating;

    @Column(nullable = false)
    @NotNull
    private Integer numberOfRates;

    @ElementCollection(targetClass=String.class)
    private List<String> actors;

    @ElementCollection(targetClass=String.class)
    private List<String> directors;

    //@ManyToMany(mappedBy = "repertoire", fetch = FetchType.LAZY)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "theatre_show",
            joinColumns = @JoinColumn(name = "theatre_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    private List<Theatre> theatres;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
    private List<Projection> projections;

    @ManyToMany(mappedBy = "shows", fetch = FetchType.LAZY)
    private List<Hall> halls;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String genre;

    public Show(){
        super();
        this.theatres = new ArrayList<>();
        this.projections = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.halls = new ArrayList<>();
    }

    public Show(Long id, @NotNull @Size(min = 2) String title, @NotNull Integer duration, @NotNull Integer price, @NotNull Double averageRating,
                @NotNull Integer numberOfRates, @NotNull List<String> actors, @NotNull List<String> directors, List<Theatre> theatres, List<Projection> projections,
                List<Hall> halls, @NotNull @Size(min = 2) String genre) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.price = price;
        this.averageRating = averageRating;
        this.numberOfRates = numberOfRates;
        this.actors = actors;
        this.directors = directors;
        this.theatres = theatres;
        this.projections = projections;
        this.halls = halls;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumberOfRates() {
        return numberOfRates;
    }

    public void setNumberOfRates(Integer numberOfRates) {
        this.numberOfRates = numberOfRates;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(List<Theatre> theatres) {
        this.theatres = theatres;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
