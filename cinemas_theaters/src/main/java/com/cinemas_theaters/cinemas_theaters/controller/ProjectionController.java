package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDisplayDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ShowRepertoireDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/projection")
public class ProjectionController {

    @Autowired
    private ProjectionService projectionServce;

    @RequestMapping(
            value = "/getProjection/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjections(@PathVariable("id") String id){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();

        List<Projection> projections = this.projectionServce.getAllProjections(Long.parseLong(id));
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

        Projection p = this.projectionServce.getById(Long.parseLong(id));
        ProjectionDisplayDTO projectionDisplayDTO = new ProjectionDisplayDTO(p.getId(), p.getShow().getTitle(), p.getDate(), p.getPrice(), p.getHall());

        return new ResponseEntity<ProjectionDisplayDTO>(projectionDisplayDTO, headers, HttpStatus.OK);
    }

}
