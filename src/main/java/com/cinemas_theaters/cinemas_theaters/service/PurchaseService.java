package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;

public interface PurchaseService {

    Boolean add(Item item, User user);
}
