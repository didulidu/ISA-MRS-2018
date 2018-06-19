package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;

import java.util.Set;

public class UserItemDTO {

    private String name;
    private String description;
    private Set<Category> categories;
    private String duration;
    private String imagePath;

    public void UserItemDTO(){}

    public void USerItemDTO(String name, String description, Set<Category> categories,
                            String duration, String imagePath){

        this.name = name;
        this.description = description;
        this.categories = categories;
        this.duration = duration;
        this.imagePath = imagePath;
    }

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public Set<Category> getCategories(){return this.categories;}
    public void setCategories(Set<Category> categories){this.categories = categories;}

    public String getDuration(){return this.duration;}
    public void setDuration(String duration){this.duration = duration;}

    public String getImagePath(){return this.imagePath;}
    public void setImagePath(String imagePath){this.imagePath = imagePath;}
}
