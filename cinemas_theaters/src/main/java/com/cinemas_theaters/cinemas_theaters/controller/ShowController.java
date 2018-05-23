package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDisplayDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ShowRepertoireDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Show;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.service.ProjectionService;
import com.cinemas_theaters.cinemas_theaters.service.ShowService;
import com.cinemas_theaters.cinemas_theaters.service.TheatreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private ProjectionService projectionService;

    //{id} je id pozorista za koji se traze show-ovi
    @RequestMapping(
            value = "/getRepertoire/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepertoire(@PathVariable("id") String id){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();
        System.out.println(Long.parseLong(id));
        List<Show> shows = this.showService.findAllShows(Long.parseLong(id));
        List<ShowRepertoireDTO> showRepertoireDTOS = new ArrayList<>();

        for (Show s: shows){
            showRepertoireDTOS.add(new ShowRepertoireDTO(s.getId(), s.getTitle(),  s.getGenre(), s.getDuration(), s.getPosterURL()));
        }

        return new ResponseEntity<List<ShowRepertoireDTO>>(showRepertoireDTOS, headers, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getProjection/{id}",
            method = RequestMethod.GET,
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
            if (p.getExist())
                projectionDisplayDTOS.add(new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getReservedSeats(), p.getHall()));
        }

        return new ResponseEntity<List<ProjectionDisplayDTO>>(projectionDisplayDTOS, headers, HttpStatus.OK);
    }



    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Show show = this.showService.getById(id);
        if (show != null){
            return ResponseEntity.ok(show);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }





    private Show convertDTOToTheatre(ShowRepertoireDTO showRepertoireDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(showRepertoireDTO, Show.class);
    }

}
