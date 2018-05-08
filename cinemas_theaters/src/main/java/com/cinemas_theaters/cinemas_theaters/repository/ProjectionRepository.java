package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    Projection save(Projection projection);

    @Query("SELECT p FROM Projection p WHERE show_show_id = :show_id")
    List<Projection> findAllProjections(@Param("show_id") Long show_id);


    Optional findById(Long id);

    Projection getById(Long id);

}
