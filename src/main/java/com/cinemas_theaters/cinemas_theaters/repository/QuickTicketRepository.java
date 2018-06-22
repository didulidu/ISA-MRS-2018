package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.QuickTicket;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuickTicketRepository extends JpaRepository<QuickTicket, Long> {
    List<QuickTicket> findAll();
    Optional findById(Long id);
    QuickTicket getById(Long id);
    List<QuickTicket> findByProjection(Projection projection);
    QuickTicket save(QuickTicket quickTicket);


    List<QuickTicket> findByTheatre(Theatre theatre);



}
