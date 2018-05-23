package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.*;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/projection")
public class ProjectionController {

    @Autowired
    private ProjectionService projectionService;
    @Autowired
    private ShowService showService;
    @Autowired
    private HallService hallService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired TheatreCinemaAdminService theatreCinemaAdminService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

//    @RequestMapping(
//            value = "/getProjection/{id}",
//            method = RequestMethod.PUT,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getProjections(@PathVariable("id") String id){
//        //JwtUser user = this.jwtService.getUser(userToken);
//        //HttpHeaders headers = new HttpHeaders();
//        //headers.add("Authorization", this.jwtService.getToken(user));
//
//        HttpHeaders headers = new HttpHeaders();
//
//        List<Projection> projections = this.projectionService.getAllProjections(Long.parseLong(id));
//        List<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<>();
//
//        //s.getId()
//        for (Projection p: projections){
//            System.out.println(p.getHall());
//            projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getHall()));
//        }
//
//        return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, headers, HttpStatus.OK);
//    }

    //pronalazi projekciju sa svojim id-jem
    @RequestMapping(
            value = "/findProjection/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjection(@PathVariable("id") String id){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();

        Projection p = this.projectionService.getById(Long.parseLong(id));

        //Collections.reverse(p.getHall().getSeats());

        ProjectionDisplayDTO projectionDisplayDTO = new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats() , p.getHall());

        return new ResponseEntity<ProjectionDisplayDTO>(projectionDisplayDTO, headers, HttpStatus.OK);
    }




    public LocalDateTime str2Date(String dateS){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime date;
        try{
            date = LocalDateTime.parse(dateS, formatter);
            return date;
        }
        catch (Exception e){
            return null;
        }
    }

    @PostMapping(
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestHeader("Authorization") String userToken, @RequestBody @Valid ProjectionDTO projDTO, BindingResult result ){
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Show film = showService.getById(projDTO.getShowId());
        Hall sala = hallService.findById(projDTO.getHallId());
        List<Projection> projekcijeSale = projectionService.findProjectionsByHall(sala.getId());
        LocalDateTime pocetak, kraj;
        boolean ok = true;
        for(Projection p : projekcijeSale){
            pocetak = str2Date(p.getDate());
            if (pocetak==null) continue;
            kraj = pocetak.plusMinutes(p.getShow().getDuration());
            LocalDateTime pocetakNovog = str2Date(projDTO.getDate());
            LocalDateTime krajNovog = pocetakNovog.plusMinutes(film.getDuration());
            if  ( (!krajNovog.isBefore(pocetak) && !krajNovog.isAfter(kraj)) || (!pocetakNovog.isAfter(kraj) && !pocetakNovog.isBefore(pocetak))){
                if(p.getExist()) ok = false;
            }
        }
        if (ok) {
            Projection nova = new Projection(projDTO.getDate(), film, sala, projDTO.getPrice());
            this.projectionService.add(nova);
            projekcijeSale.add(nova);
            ArrayList<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<ProjectionDisplayDTO>();
            for (Projection p: projectionService.getAllProjections(film.getId())){
                if (p.getExist())
                    projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats(), p.getHall()));
            }
            return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, HttpStatus.CREATED);
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @PostMapping(
            value = "/edit/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity editProjection(@RequestHeader("Authorization") String userToken, @RequestBody @Valid ProjectionDTO projDTO,@PathVariable("id") Long id, BindingResult result ) {
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if (!user.getType().equals(UserType.TheaterAndCinemaAdmin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Show film = showService.getById(projDTO.getShowId());
        Hall sala = hallService.findById(projDTO.getHallId());
        Projection choosen = projectionService.getById(id);
        if (!choosen.getReservedSeats().isEmpty()) {
            System.out.println("Postoji rezervacija");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            List<Projection> projekcijeSale = projectionService.findProjectionsByHall(sala.getId());
            LocalDateTime pocetak, kraj;
            boolean ok = true;
            for (Projection p : projekcijeSale) {
                pocetak = str2Date(p.getDate());
                if (pocetak == null) continue;
                kraj = pocetak.plusMinutes(p.getShow().getDuration());
                LocalDateTime pocetakNovog = str2Date(projDTO.getDate());
                LocalDateTime krajNovog = pocetakNovog.plusMinutes(film.getDuration());
                if ((!krajNovog.isBefore(pocetak) && !krajNovog.isAfter(kraj)) || (!pocetakNovog.isAfter(kraj) && !pocetakNovog.isBefore(pocetak))) {
                    if(p.getExist()) ok = false;
                }
            }
            if (ok) {
                choosen.setDate(projDTO.getDate());
                choosen.setHall(sala);
                choosen.setPrice(projDTO.getPrice());
                this.projectionService.save(choosen);
                ArrayList<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<ProjectionDisplayDTO>();
                for (Projection p : projectionService.getAllProjections(film.getId())) {
                    if (p.getExist())
                        projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats(), p.getHall()));
                }
                return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, HttpStatus.CREATED);
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping(
            value = "/remove/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity remove(@RequestHeader("Authorization") String userToken,@RequestBody Map<String, Long> map,@PathVariable Long id, BindingResult result ){
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Projection chosen = projectionService.getById(id);
        if(chosen.getReservedSeats().isEmpty()) {
            chosen.setExist(false);
            projectionService.save(chosen);
            ArrayList<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<ProjectionDisplayDTO>();
            for (Projection p : projectionService.getAllProjections(map.get("showId"))) {
                if (p.getExist())
                    projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats(), p.getHall()));
            }
            return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, HttpStatus.CREATED);
        }
        else return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);

    }


    @RequestMapping(
            value = "/getHalls/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getHalls(@PathVariable Long id){
        HttpHeaders headers = new HttpHeaders();
        List<Hall> halls = this.hallService.findByTheatre(id);
        List<HallDTO> hallDTOS = new ArrayList<>();
        for (Hall h : halls) {

            hallDTOS.add(new HallDTO(h.getId(), h.getName()));
        }
        return new ResponseEntity<List<HallDTO>>(hallDTOS, headers, HttpStatus.OK);
    }


    public Projection convertDTOToProjection(ProjectionDTO projectionDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(projectionDTO, Projection.class);
    }

}
