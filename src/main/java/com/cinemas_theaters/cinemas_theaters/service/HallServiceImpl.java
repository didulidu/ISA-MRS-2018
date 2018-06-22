package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;
import com.cinemas_theaters.cinemas_theaters.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("hallService")
public class HallServiceImpl implements HallService{
    @Autowired
    private HallRepository hallRepository;


    @Override
    public List<Hall> findAll(){
        return this.hallRepository.findAll();
    }

    @Override
    public Hall findById(Long id){
        Optional<Hall> dbHall = this.hallRepository.findById(id);
        if(dbHall.isPresent()){
            return dbHall.get();
        }
        else{
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id){
        if (this.hallRepository.findById(id).isPresent()){
            this.hallRepository.deleteById(id);
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

        @Override
        public void modify(Hall hall){
            this.hallRepository.save(hall);
    }

    @Override
    public Hall add(Hall hall) {
        this.hallRepository.save(hall);
        return hall;
    }

    @Override
    public List<Hall> findByTheatre(Long theatre_id) {
        return hallRepository.findByTheatre(theatre_id);
    }
}
