package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Offer;
import com.cinemas_theaters.cinemas_theaters.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinemas_theaters.cinemas_theaters.service.OfferService;
import com.cinemas_theaters.cinemas_theaters.domain.dto.OfferDTO;

@RestController
@RequestMapping(value="/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;


    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity makeOffer(@RequestBody OfferDTO offerDTO){

        Item item = itemService.findById(offerDTO.getItem_id());
        if (item != null){
            offerService.add(item, offerDTO.getBid());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}