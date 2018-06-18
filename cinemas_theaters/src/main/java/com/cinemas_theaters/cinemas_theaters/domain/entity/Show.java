package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.jws.Oneway;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`show`")

public class Show  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "show_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String posterURL;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String title;

    @Column(nullable = true)
    private String description;


    @Column(nullable = false)
    @NotNull
    private Integer duration;


    @Column(nullable = false)
    @NotNull
    private Double averageRating;

    @Column(nullable = false)
    @NotNull
    private Integer numberOfRates;

    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "actors" )
    @Column(name = "actor")
    private List<String> actors;

    @ElementCollection(targetClass=String.class)
    @CollectionTable(name = "directors" )
    @Column(name = "director")
    private List<String> directors;

    @Column(nullable = false)
    private Boolean exist;


    //@ManyToMany(mappedBy = "repertoire", fetch = FetchType.LAZY)
    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "theatre_show",
            joinColumns = @JoinColumn(name = "theatre_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )*/
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "theatre_id")
    @JsonIgnore
    private Theatre theatre;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
    private List<Projection> projections;

    //@Column(nullable = false)
    //@ManyToMany(mappedBy = "shows", fetch = FetchType.LAZY)
    //@JoinColumn(name = "hall_id")

    //private Hall hall;

    @Column(nullable = false)
    @NotNull
    @Size(min = 2)
    private String genre;

    public Show(){
        super();
        this.projections = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.exist = true;
    }

    public Show(Long id, @NotNull @Size(min = 2) String title, @NotNull Integer duration,  @NotNull Double averageRating,
                @NotNull Integer numberOfRates, @NotNull List<String> actors, @NotNull List<String> directors, Theatre theatre, List<Projection> projections,
                 @NotNull @Size(min = 2) String genre, @NotNull String posterURL, String description) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.averageRating = averageRating;
        this.numberOfRates = numberOfRates;
        this.actors = actors;
        this.directors = directors;
        this.theatre = theatre;
        this.projections = projections;
        //this.hall = hall;
        this.genre = genre;
        this.posterURL = posterURL;
        this.exist = true;
        this.description = description;
    }

    public Show(@NotNull @Size(min = 2) String title, @NotNull Integer duration, @NotNull Double averageRating,
                @NotNull Integer numberOfRates, @NotNull List<String> actors, @NotNull List<String> directors, Theatre theatre, List<Projection> projections,
                @NotNull @Size(min = 2) String genre, @NotNull String posterURL, @NotNull boolean exist, String description) {

        this.title = title;
        this.duration = duration;
        this.averageRating = averageRating;
        this.numberOfRates = numberOfRates;
        this.actors = actors;
        this.directors = directors;
        this.theatre = theatre;
        this.projections = projections;
        //this.hall = hall;
        this.genre = genre;
        this.posterURL = posterURL;
        this.exist = exist;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
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

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    /*public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }*/

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
