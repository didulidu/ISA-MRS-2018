package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("cinemaService")
public class TheatreServiceImpl implements TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;


    @Override
    public void add(String name, String address, String description){
        this.theatreRepository.save(new Theatre(name, address, description));
    }

    @Override
    public List<Theatre> getAllTheatres(){
        return this.theatreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Theatre findByName(String name){
        return this.theatreRepository.findByName(name);
    }

    @Override
    public Theatre findById(Long id){
        Optional<Theatre> dbCinema = this.theatreRepository.findById(id);
        if (dbCinema.isPresent()){
            return dbCinema.get();
        }
        else{
            return null;
        }
    }
}