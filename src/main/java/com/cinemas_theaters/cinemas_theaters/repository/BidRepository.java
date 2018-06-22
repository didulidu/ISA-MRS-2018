package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Bid;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.enums.BidStatus;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BidRepository  extends JpaRepository<Bid, Long> {

    List<Bid> findByItemAndStatusAndActive(Item item, BidStatus status, Boolean active);
    List<Bid> findByUserAndStatusAndActive(User user, BidStatus status, Boolean active);

    Optional<Bid> findByUserAndItem(User user, Item item);
}
