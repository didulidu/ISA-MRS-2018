package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheaterAdminUser;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreCinemaAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("theatreCinemaAdminService")

public class TheatreCinemaAdminServiceImpl implements TheatreCinemaAdminService{

    @Autowired
    private TheatreCinemaAdminRepository theatreCinemaAdminRepository;

    @Override
    public TheaterAdminUser findByUsername(String username){
        return this.theatreCinemaAdminRepository.findByUsername(username);
    }
}
