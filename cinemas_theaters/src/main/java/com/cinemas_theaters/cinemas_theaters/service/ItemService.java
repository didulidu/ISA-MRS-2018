package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import java.util.List;

public interface ItemService {

    Boolean add(String name, String description, Double price);
    List<Item> findAll();
    Item findById(Long id);
    Boolean deleteById(Long id);
    void modify(Item item);
}
