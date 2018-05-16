package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ItemDTO {

    private String name;
    private String description;
    private Double price;
    private String type;
    private String duration;
    private Long quantity;

    public ItemDTO(){}

    public ItemDTO(String name, String description, Double price, String type){
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public ItemDTO(String name, String description, Double price, String type, String duration, Long quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.duration = duration;
        this.quantity = quantity;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
