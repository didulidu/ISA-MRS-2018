package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item  implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Set categories = new LinkedHashSet();

    public Item(){}

    public Item(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Item(String name, String description, Set categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }


    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NotNull
    @Size(max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @Size(max = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ElementCollection(targetClass = Category.class)
    @JoinTable(name = "categories", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Set getCategories(){   return categories;  }

    public void setCategories(Set categories){    this.categories = categories;   }
}
