package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre save(Theatre theatre);

    @Query("SELECT t from Theatre t WHERE owner_id = :owner_id")
    List<Theatre> getTheatersByAdmin(@Param("owner_id") Long owner_id);

    List<Theatre> findAll();

    Theatre findByName(String name);

    Optional findById(Long id);

}
