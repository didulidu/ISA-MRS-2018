package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class TheatreDTO {
    private Long id;
    private String name;
    private String address;
    private String description;

    public TheatreDTO(){}

    public TheatreDTO(Long _id, String _name, String _address, String _description) {
        id = _id;
        name = _name;
        address = _address;
        description = _description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
