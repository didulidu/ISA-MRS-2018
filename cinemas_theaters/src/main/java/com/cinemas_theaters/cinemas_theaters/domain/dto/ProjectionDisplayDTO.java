package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;

import java.util.Date;

public class ProjectionDisplayDTO {

    private Long id;
    private String date;
    private Integer price;
    private Hall hall;

    public ProjectionDisplayDTO() {
    }

    public ProjectionDisplayDTO(Long id, String date, Integer price, Hall hall) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.hall = hall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
