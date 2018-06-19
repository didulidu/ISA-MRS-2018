package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket save(Ticket ticket);

    List<Ticket> findAll();

    Ticket getById(Long id);

//    @Query("SELECT t from Ticket tick WHERE owner_id = :owner_id")
//    List<Ticket> getTicketByAdmin(@Param("theatre_id") Long theatre_id);

    List<Ticket> findByTheatre(Theatre theatre);



}
