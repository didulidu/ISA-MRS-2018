package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ShowRepertoireDTO {

    private Long id;
    private String title;
    private String genre;
    private Integer duration;

    public ShowRepertoireDTO() {}

    public ShowRepertoireDTO(Long id, String title, String genre, Integer duration) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
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
