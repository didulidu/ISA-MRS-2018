package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;

public interface OfferService {

    void add(Item item, Double bid);
}
