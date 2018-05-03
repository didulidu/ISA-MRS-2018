package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.CinemaDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Cinema;
import com.cinemas_theaters.cinemas_theaters.service.CinemaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Cinema> findAll(){
        return this.cinemaService.findAll();
    }


    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Cinema cinema = this.cinemaService.findById(id);
        if (cinema != null){
            return ResponseEntity.ok(cinema);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody CinemaDTO cinema){
        this.cinemaService.add(cinema.getName(), cinema.getAddress(), cinema.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
