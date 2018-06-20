package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.cinemas_theaters.cinemas_theaters.repository.ReservationRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional
    public void add(Ticket ticket) {
        this.ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    @Transactional
    public void saveReservation(Reservation r) {
        this.reservationRepository.save(r);
    }


    @Override
    public Optional findById(Long id) {
        return this.ticketRepository.findById(id);
    }

    @Override
    public Ticket getById(Long id) {
        return this.ticketRepository.getById(id);
    }

    @Override
    public List<Ticket> findByTheatre(Theatre theatre) {
        return this.ticketRepository.findByTheatre(theatre);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }


}
