package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("projectionService")
public class ProjectionServiceImpl implements ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;


    @Override
    public void add(Projection show) {
        this.projectionRepository.save(show);
    }

    @Override
    public List<Projection> getAllProjections(Long show_id) {
        return this.projectionRepository.findAllProjections(show_id);
    }

    @Override
    public Optional findById(Long id) {
        return this.projectionRepository.findById(id);
    }

    @Override
    public Projection getById(Long id) {
        return this.projectionRepository.getById(id);
    }

    @Override
    public Projection findSpecificProjection(Long show_id, Long id) {
        return this.projectionRepository.findSpecificProjection(show_id, id);
    }

    @Override
    public List<Projection> findProjectionsByShow(Long show_id) {
        return this.findProjectionsByShow(show_id);
    }

    @Override
    public List<Projection> findProjectionsByHall(Long hall_id) {
        return this.projectionRepository.findProjectionsByHall(hall_id);
    }
}
