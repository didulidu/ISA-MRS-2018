package com.cinemas_theaters.cinemas_theaters.controller;

import ch.qos.logback.core.CoreConstants;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TicketReservationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Seat;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
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

            this.projectionService.add(p);

            ArrayList<Ticket> tickets = new ArrayList<>();

            for (String s: ticketReservationDTO.getSeatIds()){
                Seat seat = this.seatService.getById(Long.parseLong(s));

                Ticket ticket = new Ticket(ticketReservationDTO.getShowTitle(), ticketReservationDTO.getProjectionDate(), user, seat, p);
                tickets.add(ticket);

                this.ticketService.add(ticket);
            }



            return new ResponseEntity<ArrayList<Ticket>>(tickets, HttpStatus.CREATED);
            }
        }

    private Ticket convertDTOToTicket(TicketReservationDTO ticketReservationDTO)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(ticketReservationDTO, Ticket.class);
    }
}
