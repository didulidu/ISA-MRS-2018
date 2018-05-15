package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;
import org.springframework.stereotype.Service;

import java.util.List;
public interface HallService {
    List<Hall> findAll();
    Hall findById(Long id);
    Boolean deleteById(Long id);
    void modify(Hall item);
    Hall add(Hall hall);
}
