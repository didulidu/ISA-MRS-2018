package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    void add(Ticket show);

    List<Ticket> findAll();

    List<Ticket> findAllTicketsForAUser(Long user_id);

    Optional findById(Long id);

    Ticket findByTitle(String showTitle);

    Ticket getById(Long id);
}