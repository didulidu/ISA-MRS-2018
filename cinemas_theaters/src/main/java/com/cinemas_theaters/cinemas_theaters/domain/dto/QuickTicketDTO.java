package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class QuickTicketDTO {

    private String hallName;
    private String projectionDate;
    private Integer discount;
    private String title;
    private Integer price;
    private Long id;
    private Integer seatNumber;
    private Integer rowNumber;
    private Long theatreId;

    public QuickTicketDTO(String hallName, String projectionDate, Integer discount, String title, Integer price, Long id, Integer seatNumber, Integer rowNumber, Long theatreId) {
        this.hallName = hallName;
        this.projectionDate = projectionDate;
        this.discount = discount;
        this.title = title;
        this.price = price;
        this.id = id;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.theatreId = theatreId;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(String projectionDate) {
        this.projectionDate = projectionDate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }
}
