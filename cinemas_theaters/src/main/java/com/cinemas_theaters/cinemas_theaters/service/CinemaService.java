package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Cinema;

import java.util.List;

public interface CinemaService {

    void add(String name, String address, String description);
    List<Cinema> findAll();
    Cinema findById(Long id);
}
