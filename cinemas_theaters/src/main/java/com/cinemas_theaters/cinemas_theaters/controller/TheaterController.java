package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.TheaterDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theater;
import com.cinemas_theaters.cinemas_theaters.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cinemas_theaters.cinemas_theaters.service.TheaterService;

import java.util.List;


@RestController
@RequestMapping(value = "/theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Theater> findAll(){ return this.theaterService.findAll(); }


    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Theater t = this.theaterService.findById(id);
        if (t != null){
            return ResponseEntity.ok(t);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody TheaterDTO theater){
        this.theaterService.add(theater.getName(), theater.getAddress(), theater.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
