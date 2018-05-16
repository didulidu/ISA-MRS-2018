package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ItemService {

    Boolean add(String name, String description, String duration);
    Boolean add(String name, String description, Double price, Long quantity);
    List<Item> findAll();
    List<Item> findAll(Specification<Item> spec);
    Item findById(Long id);
    Boolean deleteById(Long id);
    void modify(Item item);
}
