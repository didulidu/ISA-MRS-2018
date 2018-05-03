package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theater;

import java.util.List;

public interface TheaterService {

    void add(String name, String address, String description);
    List<Theater> findAll();
    Theater findById(Long id);
}
