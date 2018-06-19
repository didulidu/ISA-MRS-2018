package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;

import java.util.Set;

public class TheatreItemDTO {

    private String name;
    private String description;
    private Set<Category> categories;
    private Long theatreId;
    private Double price;
    private Long quantity;

    public TheatreItemDTO(){}

    public TheatreItemDTO(String name, String description, Set<Category> categories,
                          Long theatreId, Long quantity){

        this.name = name;
        this.description = description;
        this.categories = categories;
        this.theatreId = theatreId;
        this.quantity = quantity;
    }

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public Set<Category> getCategories(){return this.categories;}
    public void setCategories(Set<Category> categories){this.categories = categories;}

    public Long getTheatreId(){return this.theatreId;}
    public void setTheatreId(Long theatreId){this.theatreId = theatreId;}

    public Double getPrice(){return this.price;}
    public void setPrice(Double price){this.price = price;}

    public Long getQuantity(){return this.quantity;}
    public void setQuantity(Long quantity){this.quantity = quantity;}
}
