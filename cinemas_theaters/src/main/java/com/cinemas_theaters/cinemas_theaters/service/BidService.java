package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface BidService {

    void add(UserItem item, User user, Double bid);
    List<Bid> findByItem(Item item);
    List<Bid> findByUser(User user);

    Bid findById(Long id);
    Boolean alreadyBided(User user, Item item);

    void acceptBid(Bid bid);
    void rejectBid(Bid bid);

    void deleteBid(Bid bid);
    void editBid(Bid bid, Double value);
}
