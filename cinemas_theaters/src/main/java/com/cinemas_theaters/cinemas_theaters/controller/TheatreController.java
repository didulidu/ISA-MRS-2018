package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.TheaterAdminUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserLoginDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.TheatreCinemaAdminService;
import com.cinemas_theaters.cinemas_theaters.service.TheatreService;

import java.util.ArrayList;
import java.util.List;

import com.cinemas_theaters.cinemas_theaters.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    TheatreCinemaAdminService theatreCinemaAdminService;


    @RequestMapping(
            value = "/getAllTheatres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTheatres(){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.save("Authorization", this.jwtService.getToken(user));
        HttpHeaders headers = new HttpHeaders();
        List<Theatre> theatres = this.theatreService.getAllTheatres();
        List<TheatreDTO> theatreDTOS = new ArrayList<>();
        for (Theatre t: theatres) {
            theatreDTOS.add(new TheatreDTO(t.getId(), t.getName(), t.getAddress(), t.getDescription(), t.getRate(), t.getType(), t.getCity(), t.getAvatarUrl()));
        }
        return new ResponseEntity<List<TheatreDTO>>(theatreDTOS, headers, HttpStatus.OK);
    }

    @PostMapping(
            value = "/getTheatersByAdmin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getTheatersByAdmin(@RequestHeader("Authorization") String adminToken){
        System.out.println("\n\n\nUSO\n\n\n");
        //JwtUser user = this.jwtService.getUser(userToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",adminToken);
        String username = this.jwtService.getUser(adminToken).getUsername();
        User user = this.userService.findByUsername(username);
        List<Theatre> theatres = this.theatreService.getTheatersByAdmin(user.getId());
        List<TheatreDTO> theatreDTOS = new ArrayList<>();
        for (Theatre t: theatres) {
            System.out.println("!!!! "+ t.getName());
            theatreDTOS.add(new TheatreDTO(t.getId(), t.getName(), t.getAddress(), t.getDescription(), t.getRate(), t.getType(), t.getCity(), t.getAvatarUrl()));
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
            TheatreDTO theatreDTO = new TheatreDTO(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getDescription(), theatre.getRate(), theatre.getType(), theatre.getCity(), theatre.getAvatarUrl());

            return ResponseEntity.ok(theatreDTO);
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
            TheatreDTO theatreDTO = new TheatreDTO(theatre.getId(),theatre.getName(),theatre.getAddress(),theatre.getDescription(), theatre.getRate(), theatre.getType(), theatre.getCity(), theatre.getAvatarUrl());
            return ResponseEntity.ok(theatreDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


//    @PostMapping(
//            value = "/edit/{id}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity editTheatre(@RequestHeader("Authorization") String userToken, @RequestBody @Valid TheatreDTO theDTO,@PathVariable("id") Long id, BindingResult result ) {
//        String username = this.jwtService.getUser(userToken).getUsername();
//        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
//        if (!user.getType().equals(UserType.TheaterAndCinemaAdmin)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//                this.theatreService.save();
//                ArrayList<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<ProjectionDisplayDTO>();
//                for (Projection p : projectionService.getAllProjections(film.getId())) {
//                    if (p.getExist())
//                        projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats(), p.getHall()));
//                }
//                return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, HttpStatus.CREATED);
//            } else
//                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        }
//    }
//


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestBody TheatreDTO theatreDTO){
        this.theatreService.add(convertDTOToTheatre(theatreDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    private Theatre convertDTOToTheatre(TheatreDTO theatreDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(theatreDTO, Theatre.class);
    }
}
