package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface ReservationRepository  extends JpaRepository<Reservation, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    @Query("SELECT r FROM Reservation r WHERE buyer_id = :user_id")
    List<Reservation> findAllReservationsForAUser(@Param("user_id") Long user_id);

    Reservation findByShowTitle(String showTitle);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Reservation getById(Long id);
}
