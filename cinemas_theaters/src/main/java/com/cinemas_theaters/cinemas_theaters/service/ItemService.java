package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ItemService {


    Boolean addUserItem(String name, String description, String duration, User user, String imagePath);
    Boolean addTheatreItem(String name, String description, Long theatreId, Double price, Long quantity);

    void acceptUserItem(Long id);
    void rejectUserItem(Long id);

    List<Item> findAllItems();
    List<UserItem> findAllUserItems();
    List<UserItem> findAllPendingUserItems();
    List<TheatreItem> findAllTheatreItems();
    List<Item> findAll(Specification<Item> spec);
    Item findById(Long id);
    Boolean deleteById(Long id);
    void modify(Item item);
}
