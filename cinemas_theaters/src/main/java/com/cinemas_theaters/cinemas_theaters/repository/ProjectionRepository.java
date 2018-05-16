package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    Projection save(Projection projection);

    @Query("SELECT p FROM Projection p WHERE show_show_id = :show_id")
    List<Projection> findAllProjections(@Param("show_id") Long show_id);

    @Query("SELECT p FROM Projection p WHERE show_show_id = :show_id and projection_id = :id")
    Projection findSpecificProjection(@Param("show_id") Long show_id, @Param("id") Long id);

    @Query("SELECT p FROM Projection p WHERE hall_hall_id = :hall_id")
    List<Projection> findProjectionsByHall(@Param("hall_id") Long hall_id);



    Optional findById(Long id);

    Projection getById(Long id);

}
