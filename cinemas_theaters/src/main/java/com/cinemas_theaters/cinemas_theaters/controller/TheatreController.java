package com.cinemas_theaters.cinemas_theaters.controller;

//import com.cinemas_theaters.cinemas_theaters.domain.dto.GraphDataDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheaterAdminUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserLoginDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.management.graph.Graph;
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

    @Autowired
    ProjectionService projectionService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterD3 = DateTimeFormatter.ofPattern("d-MMM-yyyy");



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

    @RequestMapping(
            value = "/info/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String adminToken, @RequestBody Map<String, String> map,@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", adminToken);
        String username = this.jwtService.getUser(adminToken).getUsername();
        User user = this.userService.findByUsername(username);
        if (user != null) {
            Theatre theatre = this.theatreService.findById(id);
            Double rate = theatre.getRate();
            String interval = map.get("interval");
            HashMap<String, Integer> visitations = new HashMap<String, Integer>();
            LocalDate startDate = LocalDate.now();
            if(interval.equals("day")) {
                startDate = LocalDate.now().minusDays(1);
            }
            else if(interval.equals("week")){
                    startDate = LocalDate.now().minusDays(7);
                }
                else{
                startDate = LocalDate.now().minusMonths(1);
            }
            HashMap<String, Double> showRates = new HashMap<String, Double>();
            while(!startDate.isAfter(LocalDate.now())){
                for(Show s : theatre.getShows()) {
                    showRates.put(s.getTitle(),s.getAverageRating());
                    for (Projection p : this.projectionService.getAllProjections(s.getId())) {
                        LocalDate dateOfProjection = LocalDate.parse(p.getDate(), formatter);
                        if (dateOfProjection.equals(startDate)) {
                            int visit_count = 0;
                            for (Reservation r : p.getReservations()) {
                                visit_count += r.getTickets().size();
                            }
                            visitations.put(startDate.format(formatterD3), visit_count);
                        }
                    }
                }
                startDate = startDate.plusDays(1);
            }
            //GraphDataDTO info = new GraphDataDTO(rate,visitations,showRates);


            //return new ResponseEntity<GraphDataDTO>(info, headers, HttpStatus.OK);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            System.out.println("ha ha ha ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }




    @PostMapping(
            value = "/getTheatersByAdmin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getTheatersByAdmin(@RequestHeader("Authorization") String adminToken){
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
