package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface ProjectionService {

    void add(Projection show);

    List<Projection> getAllProjections(Long show_id);

    Optional findById(Long id);

    Projection getById(Long id);

    Projection findSpecificProjection(Long show_id, Long id);

    List<Projection> findProjectionsByShow(Long show_id);

    List<Projection> findProjectionsByHall(Long hall_id);
}
