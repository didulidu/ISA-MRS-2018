package com.cinemas_theaters.cinemas_theaters.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.BidStatus;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserItemStatus;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.service.BidService;
import com.cinemas_theaters.cinemas_theaters.service.ItemService;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cinemas_theaters.cinemas_theaters.domain.dto.OfferDTO;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/bid")
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity makeBid(@RequestBody OfferDTO offerDTO,
                                    @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        Item item = itemService.findById(offerDTO.getItem_id());


        if (item != null){
            if( item instanceof UserItem ) {

                if(((UserItem) item).getUser() == user)
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

                if(((UserItem) item).getDataTime().before(new Date()))
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);

                if(this.bidService.alreadyBided(user, item))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

                this.bidService.add((UserItem) item, user, offerDTO.getBid());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteBid(@PathVariable("id") final Long id,
                                    @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Bid bid = this.bidService.findById(id);

        if (bid == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (bid.getUser() != user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.bidService.deleteBid(bid);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PutMapping(value = "/{id}/{value}")
    public ResponseEntity editBid(@PathVariable("id") final Long id,
                                  @PathVariable("value") final Double value,
                                  @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);

        Bid bid = this.bidService.findById(id);

        if (bid == null)
            return ResponseEntity.status(HttpStatus.LOCKED).body(null);

        if (bid.getUser() != user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.bidService.editBid(bid, value);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PostMapping(
            value = "/accept/{id}"
    )
    public ResponseEntity acceptBid(@PathVariable("id") final Long id,
                                    @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        Bid bid = this.bidService.findById(id);

        if (bid == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (bid.getUser() != user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.bidService.acceptBid(bid);
        this.itemService.deleteById(bid.getItem().getId());

        List<Bid> bids = this.bidService.findByItem(bid.getItem());

        for(Bid b : bids){
            if (b != bid){
                if(b.getStatus() == BidStatus.PENDING)
                    this.bidService.rejectBid(b);
            }
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PostMapping(
            value = "/reject/{id}"
    )
    public ResponseEntity rejectBid(@PathVariable("id") final Long id,
                                    @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        Bid bid = this.bidService.findById(id);

        if (bid == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (bid.getUser() != user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.bidService.rejectBid(bid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @GetMapping
    public ResponseEntity getMyBids(@RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        List<Bid> bids = this.bidService.findByUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(bids);
    }
}
