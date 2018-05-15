package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDisplayDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ShowRepertoireDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Show;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @RequestMapping(
            value = "/getProjection/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjections(@PathVariable("id") String id){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();

        List<Projection> projections = this.projectionService.getAllProjections(Long.parseLong(id));
        List<ProjectionDisplayDTO> projectionDisplayDTOS = new ArrayList<>();

        //s.getId()
        for (Projection p: projections){
            System.out.println(p.getHall());
            projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getHall()));
        }

        return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, headers, HttpStatus.OK);
    }

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
        ProjectionDisplayDTO projectionDisplayDTO = new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getHall());

        return new ResponseEntity<ProjectionDisplayDTO>(projectionDisplayDTO, headers, HttpStatus.OK);
    }

    public LocalDateTime str2Date(String dateS){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
        RegisteredUser user = this.registeredUserService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            System.out.println("Nisi admin koji bi trebalo da budes (poz/bio)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Show film = showService.getById(projDTO.getShowId());
        Hall sala = hallService.findById(projDTO.getHallId());
        List<Projection> projekcijeSale = projectionService.findProjectionsByHall(sala.getId());
        LocalDateTime pocetak, kraj;
        boolean ok = true;

        for(Projection p : projekcijeSale){
            pocetak = str2Date(p.getDate());
            System.out.println(p.getDate());
            if (pocetak==null){
                System.out.println("GRESKA");
                continue;
            }
            kraj = pocetak.plusMinutes(p.getShow().getDuration());
            LocalDateTime pocetakNovog = str2Date(projDTO.getDate());
            LocalDateTime krajNovog = pocetakNovog.plusMinutes(film.getDuration());
            if  ( (krajNovog.isAfter(pocetak) && krajNovog.isBefore(kraj)) || (pocetakNovog.isBefore(kraj) && pocetakNovog.isAfter(pocetak))){
                ok = false;
            }
        }
        if (ok) {
            System.out.println("Dodaje");
            Projection nova = new Projection(projDTO.getId(),projDTO.getDate(), film, sala, projDTO.getPrice());
            this.projectionService.add(nova);
            projekcijeSale.add(nova);
            return new ResponseEntity<List<Projection>>(projekcijeSale, HttpStatus.CREATED);
        }
        else
            System.out.println("Nije dodao");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    public Projection convertDTOToProjection(ProjectionDTO projectionDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(projectionDTO, Projection.class);
    }

}
