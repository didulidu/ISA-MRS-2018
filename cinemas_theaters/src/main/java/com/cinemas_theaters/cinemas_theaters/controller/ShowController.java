package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ProjectionDisplayDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.ShowRepertoireDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TheatreCinemaAdminService theatreCinemaAdminService;


    //{id} je id pozorista za koji se traze show-ovi
    @RequestMapping(
            value = "/getRepertoire/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepertoire(@PathVariable("id") String id){
        //JwtUser user = this.jwtService.getUser(userToken);
        //HttpHeaders headers = new HttpHeaders();
        //headers.save("Authorization", this.jwtService.getToken(user));

        HttpHeaders headers = new HttpHeaders();
        System.out.println(Long.parseLong(id));
        List<Show> shows = this.showService.findAllShows(Long.parseLong(id));
        List<ShowRepertoireDTO> showRepertoireDTOS = new ArrayList<>();

        for (Show s: shows){
            if(s.getExist())
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
        //headers.save("Authorization", this.jwtService.getToken(user));

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


    @PostMapping(
            value = "/save/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity add(@RequestHeader("Authorization") String userToken, @RequestBody @Valid ShowRepertoireDTO showDTO,@PathVariable("id") Long id, BindingResult result ){
        System.out.println("USO");
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            System.out.println("Ne odgovara tip korisnika *****************88");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

// dobij pozoriste po idju koji dobija ova funkcija
// proveri da li u tom pozoristu vec postoji show sa tim imenom (a da je bas bioskop ili bas pozoriste)
// ako je ok ime, pravi show i dodaj ga u listu show-ova dobijenog pozorista, i sacuvaj u bazu.

        Theatre pozoriste = theatreService.findById(id);
        boolean ok = true;
        for(Show p : pozoriste.getShows()){
            if(p.getTitle().equals(showDTO.getTitle()) && p.getExist()) ok = false;

        }
        System.out.println("Stigo dovdeee ****************************" + showDTO.getPosterURL());
        if (ok) {
            String[] niz = showDTO.getActors().split(",");
            List<String> glumci = new ArrayList<String>();
            for(String  s : niz) glumci.add(s);
            String[] niz2 = showDTO.getActors().split(",");
            List<String> direktori = new ArrayList<String>();
            for(String  s : niz2) direktori.add(s);
            List<Projection> prjs = new ArrayList<Projection>();
            Show nova = new Show(showDTO.getTitle(),showDTO.getDuration(),0.0,0,glumci,direktori,pozoriste,prjs,showDTO.getGenre(),showDTO.getPosterURL(), true, showDTO.getDescription());
            this.showService.save(nova);
            pozoriste.getShows().add(nova);
            ArrayList<ShowRepertoireDTO> showDTOS = new ArrayList<ShowRepertoireDTO>();
            for (Show p: showService.findAllShows(pozoriste.getId())){
                if (p.getExist()) {
                    String glumci_str = String.join(",", glumci);
                    String direktori_str = String.join(",", direktori);
                    showDTOS.add(new ShowRepertoireDTO(p.getId(), p.getTitle(),p.getGenre(),p.getDuration(),p.getPosterURL(), glumci_str, direktori_str,p.getDescription()));
                }
            }
            return new ResponseEntity<List<ShowRepertoireDTO>>(showDTOS, HttpStatus.CREATED);
        }
        else
            System.out.println("IME VEC POSTOJI");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }


    @PostMapping(
            value = "/remove/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity remove(@RequestHeader("Authorization") String userToken, @RequestBody Map<String, Long> map, @PathVariable String id, BindingResult result ){
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Show chosen = showService.getById(Long.parseLong(id));
        System.out.println("**************************" + chosen.getTitle()+"***"+map.get("theatreId"));
        if(chosen.getProjections().isEmpty()) {
            chosen.setExist(false);
            showService.saveAndFlush(chosen);
            ArrayList<ShowRepertoireDTO> showDTOS = new ArrayList<ShowRepertoireDTO>();
            System.out.println("Stigoo i dovde, id pozorista je " + map.get("theatreId"));
            for (Show p : showService.findAllShows(map.get("theatreId"))) {
                if (p.getExist()) {
                    showDTOS.add(new ShowRepertoireDTO(p.getId(), p.getTitle(), p.getGenre(), p.getDuration(), p.getPosterURL(), String.join(",", p.getActors()), String.join(",", p.getDirectors()), p.getDescription()));
                    System.out.println("DODATO");
                }
            }
            return new ResponseEntity<List<ShowRepertoireDTO>>(showDTOS, HttpStatus.CREATED);
        }
        else return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);

    }

    @PostMapping(
            value = "/edit/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity editProjection(@RequestHeader("Authorization") String userToken, @RequestBody @Valid ShowRepertoireDTO showDTO,@PathVariable("id") Long id, BindingResult result ) {
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if (!user.getType().equals(UserType.TheaterAndCinemaAdmin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Show film = showService.getById(showDTO.getId());

        if(!film.getTheatre().getTheaterAdminUser().getId().equals(user.getId())){
            System.out.println("NE GLEDAJ U TUDJ TANJIR");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        if (!film.getProjections().isEmpty()) {
            System.out.println("Postoji rezervacija");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {

                film.setTitle(showDTO.getTitle());
                ArrayList<String> glumci = new ArrayList<String>();
                String[] nizg = showDTO.getActors().split(",");
                for(String s : nizg) glumci.add(s);
                film.setActors(glumci);
                ArrayList<String> dirs = new ArrayList<String>();
                String[] nizd = showDTO.getDirectors().split(",");
                for(String s : nizd) dirs.add(s);
                film.setDirectors(dirs);

                film.setDuration(showDTO.getDuration());
                film.setDescription(showDTO.getDescription());
                film.setGenre(showDTO.getGenre());
                film.setPosterURL(showDTO.getPosterURL());
            showService.saveAndFlush(film);

            ArrayList<ShowRepertoireDTO> showDTOS = new ArrayList<ShowRepertoireDTO>();
            for (Show p : showService.findAllShows(id)) {
                if (p.getExist()) {
                    showDTOS.add(new ShowRepertoireDTO(p.getId(), p.getTitle(), p.getGenre(), p.getDuration(), p.getPosterURL(), String.join(",", p.getActors()), String.join(",", p.getDirectors()), p.getDescription()));
                }
            }
            return new ResponseEntity<List<ShowRepertoireDTO>>(showDTOS, HttpStatus.CREATED);
        }
    }



//
//    @GetMapping(
//            value = "/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity findById(@PathVariable final Long id){
//        Show show = this.showService.getById(id);
//        if (show != null){
//            return ResponseEntity.ok(show);
//        }
//        else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }





    private Show convertDTOToTheatre(ShowRepertoireDTO showRepertoireDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(showRepertoireDTO, Show.class);
    }

}
