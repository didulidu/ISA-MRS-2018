package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Show;
import com.cinemas_theaters.cinemas_theaters.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("showService")
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;


    @Override
    public void add(Show show) {
        this.showRepository.save(show);
    }

    @Override
    public List<Show> findAllShows(Long theater_id) {
        return showRepository.findAllShows(theater_id);
    }

    @Override
    public Optional findById(Long id) {
        return showRepository.findById(id);
    }

    @Override
    public Show getById(Long id) {
        return showRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Show findByTitle(String name) {
        return showRepository.findByTitle(name);
    }
}
