package com.cinemas_theaters.cinemas_theaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import com.cinemas_theaters.cinemas_theaters.domain.dto.ItemDTO;
import com.cinemas_theaters.cinemas_theaters.service.ItemService;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;

import java.util.List;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody ItemDTO item){
        Boolean success = this.itemService.add(item.getName(), item.getDescription(), item.getPrice());
        if (success){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Item> findAll(){
        return this.itemService.findAll();
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Item item = this.itemService.findById(id);
        if (item != null){
            return ResponseEntity.ok(item);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deleteById(@PathVariable final Long id){
        Boolean success = this.itemService.deleteById(id);
        if (success != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity modify(@PathVariable final Long id, @RequestBody ItemDTO itemDTO){
        Item item = this.itemService.findById(id);
        if(item != null){
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            this.itemService.modify(item);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        else{
            this.itemService.add(itemDTO.getName(), itemDTO.getDescription(), itemDTO.getPrice());

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }

    }
}
