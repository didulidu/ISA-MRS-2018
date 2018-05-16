package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;

import java.util.Date;
import java.util.List;

public class ProjectionDisplayDTO {

    private Long id;
    private String showTitle;
    private String date;
    private Integer price;
    private List<String> reservedSeats;
    private Hall hall;

    public ProjectionDisplayDTO() {
    }

    public ProjectionDisplayDTO(Long id, String showTitle, String date, Integer price, List<String> reservedSeats, Hall hall) {
        this.id = id;
        this.showTitle = showTitle;
        this.date = date;
        this.price = price;
        this.hall = hall;
        this.reservedSeats = reservedSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
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

    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
