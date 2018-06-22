package com.cinemas_theaters.cinemas_theaters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;

import java.util.List;

@Component
public interface HallRepository extends JpaRepository<Hall, Long> {
     Hall save(Hall hall);

     @Query("SELECT h from Hall h WHERE theatre_theatre_id = :theatre_id")
     List<Hall> findByTheatre(@Param("theatre_id") Long theatre_id);


}
