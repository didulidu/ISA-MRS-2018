package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;

import java.util.List;

public interface TheatreService {

    void add(String name, String address, String description);

    List<Theatre> getAllTheatres();

    Theatre findById(Long id);

    Theatre findByName(String name);
}