package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.StructureType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatre")
public class Theatre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theatre_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String avatarUrl;

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

    @Column(nullable=true)
    private Double rate;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("theatre")
    private List<Show> repertoire;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("theatre")
    private List<Hall> halls;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("theatre")
    @JsonIgnore
    private List<Ticket> tickets;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StructureType type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private TheaterAdminUser theaterAdminUser;

    public Theatre(){
        this.repertoire = new ArrayList<>();
        this.halls = new ArrayList<>();
    }

    public Theatre(String _name, String _address, String _description){
        name = _name;
        address = _address;
        description = _description;
        avatarUrl = "Milojko"; // za sada jer ga nema u konstruktoru a nesme null, lako cemo dodati
        city = "Novi Sad"; // za sada jer ga nema u konstruktoru a nesme null, lako cemo dodati
        repertoire = new ArrayList<>();
        halls = new ArrayList<>();
        type = StructureType.Cinema;
    }

    public Theatre(Long id, @NotNull String avatarUrl, @NotNull @Size(min = 2) String name, @NotNull @Size(min = 2) String description, @NotNull @Size(min = 2) String address, @NotNull @Size(min = 2) String city, Double rate, List<Show> repertoire, List<Hall> halls, StructureType type, TheaterAdminUser owner) {
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.rate = rate;
        this.repertoire = repertoire;
        this.halls = halls;
        this.type = type;
        this.theaterAdminUser = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Show> getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(List<Show> repertoire) {
        this.repertoire = repertoire;
    }

    public StructureType getType() {
        return type;
    }

    public void setType(StructureType type) {
        this.type = type;
    }

    public List<Show> getShows() {
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public TheaterAdminUser getTheaterAdminUser() {
        return theaterAdminUser;
    }

    public void setTheaterAdminUser(TheaterAdminUser owner) {
        this.theaterAdminUser = owner;
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "id=" + id +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", rate=" + rate +
                ", type=" + type +
                ", theaterAdminUser=" + theaterAdminUser.getId() +
                '}';
    }
}
