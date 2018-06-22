package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Seat;
import com.cinemas_theaters.cinemas_theaters.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("seatService")
public class SeatServiceImpl implements  SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void save(Seat seat) {
        this.seatRepository.save(seat);
    }

    @Override
    public Seat getById(Long id) {
        return this.seatRepository.getById(id);
    }
}
