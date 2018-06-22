package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Seat;

public interface SeatService {
    void save(Seat seat);

    Seat getById(Long id);
}
