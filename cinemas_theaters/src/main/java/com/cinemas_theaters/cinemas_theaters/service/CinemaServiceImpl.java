package com.cinemas_theaters.cinemas_theaters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Cinema;
import com.cinemas_theaters.cinemas_theaters.repository.CinemaRepository;

import java.util.List;
import java.util.Optional;

@Service("cinemaService")
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;


    @Override
    public void add(String name, String address, String description){
        this.cinemaRepository.save(new Cinema(name, address, description));
    }

    @Override
    public List<Cinema> findAll(){
        return this.cinemaRepository.findAll();
    }

    @Override
    public Cinema findById(Long id){
        Optional<Cinema> dbCinema = this.cinemaRepository.findById(id);
        if (dbCinema.isPresent()){
            return dbCinema.get();
        }
        else{
            return null;
        }
    }
}
