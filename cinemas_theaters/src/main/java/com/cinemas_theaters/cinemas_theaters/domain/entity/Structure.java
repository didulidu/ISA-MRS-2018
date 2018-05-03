package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Structure implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String avatarUrl;

    @Column(nullable = false)
    @NotNull
    private Double averageRating;

    @Column(nullable = false)
    @NotNull
    private Integer numberOfRates;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String name;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String description;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String address;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String city;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    private ArrayList<Show> repertoire;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    private ArrayList<Projection> projections;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    private List<Hall> halls;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    private ArrayList<String> actors;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    private ArrayList<String> directors;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String genre;

    public ArrayList<Show> getShows() {
        return repertoire;
    }

    public void setShows(ArrayList<Show> repertoire) {
        this.repertoire = repertoire;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public ArrayList<Projection> getProjections() {
        return projections;
    }

    public void setProjections(ArrayList<Projection> projections) {
        this.projections = projections;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
