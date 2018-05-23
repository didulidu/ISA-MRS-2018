package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;

public class ProjectionDTO {
    private String date;
    private Integer price;
    private Long hallId;
    private Long showId;

    public ProjectionDTO() {
    }

    public ProjectionDTO(String date, Integer price, Long hallId, Long showId) {
        this.date = date;
        this.price = price;
        this.hallId = hallId;
        this.showId = showId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }
}
