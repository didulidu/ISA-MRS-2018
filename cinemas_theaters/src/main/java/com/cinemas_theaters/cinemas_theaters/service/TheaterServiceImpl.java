package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theater;
import com.cinemas_theaters.cinemas_theaters.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("theaterService")
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public void add(String name, String address, String description){
        this.theaterRepository.save(new Theater(name, address, description));
    }

    @Override
    public List<Theater> findAll(){
        return this.theaterRepository.findAll();
    }

    @Override
    public Theater findById(Long id){
        Optional<Theater> dbTheater = this.theaterRepository.findById(id);
        if (dbTheater.isPresent()){
            return dbTheater.get();
        }
        else{
            return null;
        }
    }

}
