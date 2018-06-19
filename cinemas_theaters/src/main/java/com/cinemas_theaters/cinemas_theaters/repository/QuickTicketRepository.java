package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.QuickTicket;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuickTicketRepository extends JpaRepository<QuickTicket, Long> {
    List<QuickTicket> findAll();
    Optional findById(Long id);
    QuickTicket getById(Long id);
    List<QuickTicket> findByProjection(Projection projection);
    QuickTicket save(QuickTicket quickTicket);


}
