package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ShowRepertoireDTO {

    private Long id;
    private String posterURL;
    private String title;
    private String genre;
    private Integer duration;
    private String actors;
    private String directors;
    private String description;

    public ShowRepertoireDTO() {}

    public ShowRepertoireDTO(Long id, String title, String genre, Integer duration, String posterURL) {
        this.id = id;
        this.posterURL = posterURL;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public ShowRepertoireDTO(Long id, String title, String genre, Integer duration, String posterURL, String actors, String directors, String description) {
        this.id = id;
        this.posterURL = posterURL;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.actors = actors;
        this.directors = directors;
        this.description = description;
    }

    public ShowRepertoireDTO(String title, String genre, Integer duration, String posterURL, String actors, String directors, String description) {
        this.posterURL = posterURL;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.actors = actors;
        this.directors = directors;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
