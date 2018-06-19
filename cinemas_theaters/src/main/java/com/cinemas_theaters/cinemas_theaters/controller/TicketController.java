package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.FriendDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.QuickTicketCreatorDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.QuickTicketDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.FriendDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TicketReservationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.repository.ReservationRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import com.cinemas_theaters.cinemas_theaters.service.*;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;
import jdk.net.SocketFlow;
import jdk.net.SocketFlow.Status;
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

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HallService hallService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ReservationRepository reservationRepository;




    @RequestMapping(
            value = "/reservation",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ticketReservation(@RequestHeader("Authorization") String userToken, @RequestBody @Valid TicketReservationDTO ticketReservationDTO, BindingResult result) {
        if(result.hasErrors()){
            // sistemske validacije podataka nisu zadovoljene
            System.out.println(result.getAllErrors());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else if(ticketReservationDTO.getInvitedFriends().size()>ticketReservationDTO.getSeatIds().size()){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        else {
            System.out.println(ticketReservationDTO.getShowTitle());
            Projection p = this.projectionService.getById(Long.parseLong(ticketReservationDTO.getProjectionId()));

            String username = this.jwtService.getUser(userToken).getUsername();
            RegisteredUser user = this.registeredUserService.findByUsername(username);

            List<String> ids = p.getReservedSeats();
            for(String seatID: ticketReservationDTO.getSeatIds()){

                ids.add(seatID);
            }
            p.setReservedSeats(ids);

            ArrayList<RegisteredUser> invitedFriends = this.registeredUserService.approveInvitations(ticketReservationDTO.getInvitedFriends(), user);

            Reservation reservation = new Reservation(ticketReservationDTO.getShowTitle(), ticketReservationDTO.getProjectionDate(), user);
            List<Invitation> invitations = this.registeredUserService.sendInvitations(invitedFriends, user, reservation);

            reservation.setInvitations(invitations);

            this.reservationRepository.save(reservation);

            ArrayList<Ticket> tickets = new ArrayList<>();
            for (String s: ticketReservationDTO.getSeatIds()){
                Seat seat = this.seatService.getById(Long.parseLong(s));

                //Ticket ticket = new Ticket(ticketReservationDTO.getShowTitle(), ticketReservationDTO.getProjectionDate(), user, seat, p);

                Ticket ticket = new Ticket(seat, p, theatreRepository.getById(132L), reservation);

                tickets.add(ticket);

                this.ticketService.add(ticket);
            }

            reservation.setTickets(tickets);
            reservation.setProjection(p);

            p.getReservations().add(reservation);

            this.reservationRepository.save(reservation);
            this.projectionService.save(p);

            this.emailService.sendReservedTicketInfo(user, reservation);

            this.emailService.sendReservedTicketInfo(user, reservation);

            return new ResponseEntity<TicketReservationDTO>(ticketReservationDTO, HttpStatus.CREATED);
            }
        }

    @GetMapping(
            value = "/quicktickets/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )public ResponseEntity<?> getQuickTickets(@RequestHeader("Authorization") String userToken, @PathVariable("id") String id, BindingResult result){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",userToken);
        String username = this.jwtService.getUser(userToken).getUsername();
        User user = this.userService.findByUsername(username);
        if(!user.getType().equals(UserType.TheaterAndCinemaAdmin)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Theatre pozoriste = this.theatreRepository.getById(Long.parseLong(id));
        if(pozoriste == null){
            System.out.println("ne valja id pozoristas");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Ticket> sve_karte = this.ticketService.findByTheatre(pozoriste);
        List<QuickTicketDTO> sve_brze_karte = new ArrayList<QuickTicketDTO>();
        for(Ticket t : sve_karte){
            LocalDateTime datum_projekcije = ShowController.str2Date(t.getProjection().getDate());
            if(t instanceof QuickTicket && datum_projekcije.isAfter(LocalDateTime.now())){
                sve_brze_karte.add(new QuickTicketDTO(t.getProjection().getHall().getName(),t.getProjection().getDate(),((QuickTicket)t).getDiscount(),t.getProjection().getShow().getTitle(),t.getProjection().getPrice(),t.getId(),t.getSeat().getChairNumber(),t.getSeat().getChairRow(), Long.parseLong(id)));
            }
        }
        return new ResponseEntity<List<QuickTicketDTO>>(sve_brze_karte, headers, HttpStatus.OK);
    }

    @PostMapping(
            value = "/quicktickets/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createQuickTicket(@RequestHeader("Authorization") String userToken, @RequestBody @Valid QuickTicketCreatorDTO quickTicketDTO,@PathVariable("id") String id, BindingResult result) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", userToken);
        String username = this.jwtService.getUser(userToken).getUsername();
        User user = this.userService.findByUsername(username);
        if (!user.getType().equals(UserType.TheaterAndCinemaAdmin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Theatre pozoriste = this.theatreRepository.getById(Long.parseLong(id));
        Projection projekcija = this.projectionService.getById(quickTicketDTO.getProjectionId());
        Seat sediste = null;
        for(Seat s : projekcija.getHall().getSeats()){
            if(s.getChairNumber() == quickTicketDTO.getSeatNum() && s.getChairRow() == quickTicketDTO.getSeatRow()){
                sediste = s;
                break;
            }
        }
        if(sediste == null){
            System.out.println("ne nalazi sediste");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Ticket newTicket = new QuickTicket(sediste,projekcija,pozoriste,null,quickTicketDTO.getDiscount());

        this.ticketService.save(newTicket);

        List<QuickTicketDTO> sve_brze_karte = new ArrayList<QuickTicketDTO>();
        for(Ticket t : this.ticketService.findAll()){
            LocalDateTime datum_projekcije = ShowController.str2Date(t.getProjection().getDate());
            if(t instanceof QuickTicket && datum_projekcije.isAfter(LocalDateTime.now())){
                sve_brze_karte.add(new QuickTicketDTO(t.getProjection().getHall().getName(),t.getProjection().getDate(),((QuickTicket)t).getDiscount(),t.getProjection().getShow().getTitle(),t.getProjection().getPrice(),t.getId(),t.getSeat().getChairNumber(),t.getSeat().getChairRow(),t.getTheatre().getId()));
            }
        }
        return new ResponseEntity<List<QuickTicketDTO>>(sve_brze_karte, headers, HttpStatus.OK);
    }




    private Ticket convertDTOToTicket(TicketReservationDTO ticketReservationDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ticketReservationDTO, Ticket.class);
    }
}
