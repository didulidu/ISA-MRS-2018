package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class OfferDTO {

    private Long item_id;

    private Double bid;

    public OfferDTO() { }

    public OfferDTO(Long item_id, Double bid) {
        this.item_id = item_id;
        this.bid = bid;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }
}
