package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;

import java.util.List;

public interface TheatreService {

    void add(Theatre theatre);

    List<Theatre> getAllTheatres();

    List<Theatre> getTheatersByAdmin(Long id);

    Theatre findById(Long id);

    Theatre findByName(String name);
}
