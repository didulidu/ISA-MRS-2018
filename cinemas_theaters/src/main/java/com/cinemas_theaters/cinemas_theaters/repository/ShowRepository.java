package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowRepository  extends JpaRepository<Show, Long> {
    Show save(Show show);

    @Query("SELECT s FROM Show s WHERE theatre_theatre_id = :theater_id")
    List<Show> findAllShows(@Param("theater_id") Long theater_id);

    Show findByTitle(String title);

    Optional findById(Long id);

    Show getById(Long id);
}
