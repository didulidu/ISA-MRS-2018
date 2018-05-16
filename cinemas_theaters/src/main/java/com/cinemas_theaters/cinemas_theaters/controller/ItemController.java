package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;
import com.cinemas_theaters.cinemas_theaters.repository.ItemSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import com.cinemas_theaters.cinemas_theaters.domain.dto.ItemDTO;
import com.cinemas_theaters.cinemas_theaters.service.ItemService;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody ItemDTO item){
        Boolean success;
        if(item.getType().equals("user")) {
            success = this.itemService.add(item.getName(), item.getDescription(), item.getDuration());
        }else if (item.getType().equals("theatre")){
            success = this.itemService.add(item.getName(), item.getDescription(), item.getPrice(), item.getQuantity());
        }else{
            success = false;
        }
        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(item.getType());
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
            if (item instanceof TheatreItem) {
                ((TheatreItem)item).setPrice(itemDTO.getPrice());
            }
            this.itemService.modify(item);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping( value = "/search")
    public ResponseEntity searchItems(@RequestParam(value = "search") String search){
        ItemSpecificationsBuilder builder = new ItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()){
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Item> spec = builder.build();


        return ResponseEntity.ok(itemService.findAll(spec));
    }
}
