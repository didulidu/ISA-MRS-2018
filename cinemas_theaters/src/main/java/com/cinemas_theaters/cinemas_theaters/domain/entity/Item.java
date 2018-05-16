package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item  implements Serializable {

    private Long id;

    private String name;

    private String description;

    public Item(){}

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
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

}
