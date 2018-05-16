package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.repository.OfferRepository;

@Service("offerService")
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public void add(UserItem item, Double bid){
        this.offerRepository.save(new Offer(item, bid));
    }

    @Override
    public void add(TheatreItem item){
        this.offerRepository.save(new Offer(item));
    }

}
