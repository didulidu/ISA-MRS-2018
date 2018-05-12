package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket save(Ticket ticket);

    List<Ticket> findAll();

    @Query("SELECT t FROM Ticket t WHERE user_user_id = :user_id")
    List<Ticket> findAllTicketsForAUser(@Param("user_id") Long user_id);

    Ticket findByShowTitle(String showTitle);

    Ticket getById(Long id);
}
