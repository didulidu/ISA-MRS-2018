package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ItemDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.service.TheatreService;

import java.util.ArrayList;
import java.util.List;

import jdk.net.Sockets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/home"})
public class HomeController {

    @Autowired
    private TheatreService theatreService;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody TheatreDTO item){
        this.theatreService.add(item.getName(), item.getAddress(), item.getDescription());
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TheatreDTO> findAll(){
        HttpHeaders headers = new HttpHeaders();
        List<Theatre> theatres = this.theatreService.getAllTheatres();
        List<TheatreDTO> theatreDTOS = new ArrayList<>();
        for (Theatre t: theatres){
            theatreDTOS.add(new TheatreDTO(t.getName(),  t.getAddress(), t.getDescription()));
        }

        return theatreDTOS;
    }

}
