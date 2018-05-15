package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.StructureType;

public class TheatreDTO {
    private String name;
    private String address;
    private String description;
    private Double rate;
    private StructureType type;
    private Long id;
    private String avatarUrl;
    private String city;


    public TheatreDTO(){}

    public TheatreDTO( Long _id, String _name, String _address, String _description, Double _rate, StructureType _type, String _city, String _avatarUrl) {
        name = _name;
        address = _address;
        description = _description;
        rate = _rate;
        type = _type;
        id = _id;
        city = _city;
        avatarUrl = _avatarUrl;
    }

    public TheatreDTO( String _name, String _address, String _description, Double _rate, StructureType _type, String _city, String _avatarUrl){
        name = _name;
        address = _address;
        description = _description;
        rate = _rate;
        type = _type;
        city = _city;
        avatarUrl = _avatarUrl;
    }



    public Long getId(){ return id;}

    public void setId(Long id){ this.id = id;}

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public StructureType getType() {
        return type;
    }

    public void setType(StructureType type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
