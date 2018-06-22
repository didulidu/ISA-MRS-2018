package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.BidStatus;
import com.cinemas_theaters.cinemas_theaters.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service("bidService")
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Override
    @Transactional
    public void add(UserItem item, User user, Double bid){
        this.bidRepository.save(new Bid(user, item, bid, BidStatus.PENDING));
    }

    @Override
    @Transactional
    public Boolean alreadyBided(User user, Item item){
        Optional<Bid> bidDAO = this.bidRepository.findByUserAndItem(user, item);
        if (bidDAO.isPresent())
            return true;

        return false;
    }

    @Override
    @Transactional
    public List<Bid> findByItem(Item item) {
        return this.bidRepository.findByItemAndStatusAndActive(item, BidStatus.PENDING, true);
    }

    @Override
    @Transactional
    public List<Bid> findByUser(User user){return this.bidRepository.findByUserAndStatusAndActive(user, BidStatus.PENDING, true);}

    @Override
    @Transactional
    public Bid findById(Long id){
        Optional<Bid> bid = this.bidRepository.findById(id);
        if(bid.isPresent())
            return bid.get();

        return null;
    }

    @Override
    @Transactional
    public void acceptBid(Bid bid){
        bid.setStatus(BidStatus.ACCEPTED);

        this.bidRepository.save(bid);
    }

    @Override
    @Transactional
    public void rejectBid(Bid bid){
        bid.setStatus(BidStatus.REJECTED);
        this.bidRepository.save(bid);
    }

    @Override
    @Transactional
    public void deleteBid(Bid bid){
        bid.setActive(false);
        this.bidRepository.save(bid);
    }

    @Override
    @Transactional
    public void editBid(Bid bid, Double value){
        bid.setBid(value);
        this.bidRepository.save(bid);
    }
}
