package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class CinemaDTO {

    private String name;
    private String address;
    private String description;

    public CinemaDTO(){}

    public CinemaDTO(String _name, String _address, String _description) {
        name = _name;
        address = _address;
        description = _description;
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
