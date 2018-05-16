package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;

public interface OfferService {

    void add(UserItem item, Double bid);
    void add(TheatreItem item);
}
