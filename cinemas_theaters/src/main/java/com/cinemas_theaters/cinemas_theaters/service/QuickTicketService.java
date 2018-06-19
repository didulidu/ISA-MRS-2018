package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.QuickTicket;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import java.util.List;
import java.util.Optional;

public interface QuickTicketService {


    List<QuickTicket> findAll();

    QuickTicket save(QuickTicket quickTicket);

    QuickTicket saveAndFlush(QuickTicket quickTicket);

    Optional findById(Long id);

    List<QuickTicket> findByProjection(Projection projection);

}
