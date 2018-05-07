package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.service.TheatreService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @RequestMapping(
            value = "/getAllTheatres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTheatres(){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();

        List<Theatre> theatres = this.theatreService.getAllTheatres();
        List<TheatreDTO> theatreDTOS = new ArrayList<>();

        for (Theatre t: theatres){
            theatreDTOS.add(new TheatreDTO(t.getName(),  t.getAddress(), t.getDescription()));
        }

        return new ResponseEntity<List<TheatreDTO>>(theatreDTOS, headers, HttpStatus.OK);
    }


    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Theatre theatre = this.theatreService.findById(id);
        if (theatre != null){
            return ResponseEntity.ok(theatre);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping(
            value = "/visit/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findByName(@PathVariable final String name){
        Theatre theatre = this.theatreService.findByName(name);
        if (theatre != null){
            System.out.println("VRAACA GA");
            TheatreDTO theatreDTO = new TheatreDTO(theatre.getName(),theatre.getAddress(),theatre.getDescription());
            return ResponseEntity.ok(theatreDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody TheatreDTO cinema){
        this.theatreService.add(cinema.getName(), cinema.getAddress(), cinema.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
