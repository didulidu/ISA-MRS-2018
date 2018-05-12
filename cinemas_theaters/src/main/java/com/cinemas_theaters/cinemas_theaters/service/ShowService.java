package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Show;

import java.util.List;
import java.util.Optional;

public interface ShowService {
    void add(Show show);

    List<Show> findAllShows(Long theater_id);

    Optional findById(Long id);

    Show findByTitle(String name);

    Show getById(Long id);
}
