package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    void add(Ticket show);

    List<Ticket> findAll();

    Reservation getReservation(Long id);

    void saveReservation(Reservation r);

    Optional findById(Long id);

    Ticket getById(Long id);

    List<Ticket> findByTheatre(Theatre theatre);

    Ticket save(Ticket ticket);


}
