package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.TicketReservationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.repository.ReservationRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import com.cinemas_theaters.cinemas_theaters.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private JwtService jwtService;

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



            ArrayList<Ticket> tickets = new ArrayList<>();

            Reservation r = new Reservation(ticketReservationDTO.getShowTitle(), ticketReservationDTO.getProjectionDate(), user);

            this.reservationRepository.save(r);

            System.out.println("%^%^%^%^%^%^%^%^%^%^%^%^%^%^%^%^%^%^ " + r.getId());

            for (String s: ticketReservationDTO.getSeatIds()){
                Seat seat = this.seatService.getById(Long.parseLong(s));

                //Ticket ticket = new Ticket(ticketReservationDTO.getShowTitle(), ticketReservationDTO.getProjectionDate(), user, seat, p);

                Ticket ticket = new Ticket(seat, p, theatreRepository.getById(132L), r);

                tickets.add(ticket);

                this.ticketService.add(ticket);
            }

            r.setTickets(tickets);
            r.setProjection(p);

            p.getReservations().add(r);

            this.reservationRepository.save(r);
            this.projectionService.save(p);

            return new ResponseEntity<TicketReservationDTO>(ticketReservationDTO, HttpStatus.CREATED);
            }
        }

    private Ticket convertDTOToTicket(TicketReservationDTO ticketReservationDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ticketReservationDTO, Ticket.class);
    }
}
