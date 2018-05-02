package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ItemDTO {

    private String name;
    private String description;
    private Double price;

    public ItemDTO(){}

    public ItemDTO(String _name, String _description, Double _price){
        name = _name;
        description = _description;
        price = _price;
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
}
