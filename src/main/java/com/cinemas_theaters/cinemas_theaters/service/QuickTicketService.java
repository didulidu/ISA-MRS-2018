package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.QuickTicket;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;

import java.util.List;
import java.util.Optional;

public interface QuickTicketService {


    List<QuickTicket> findAll();

    QuickTicket save(QuickTicket quickTicket);

    QuickTicket saveAndFlush(QuickTicket quickTicket);

    QuickTicket findById(Long id);

    List<QuickTicket> findByProjection(Projection projection);

    List<QuickTicket> findByTheatre(Theatre theatre);
}
