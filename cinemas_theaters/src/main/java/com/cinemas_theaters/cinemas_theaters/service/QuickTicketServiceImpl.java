package com.cinemas_theaters.cinemas_theaters.service;


import com.cinemas_theaters.cinemas_theaters.domain.entity.Projection;
import com.cinemas_theaters.cinemas_theaters.domain.entity.QuickTicket;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.repository.QuickTicketRepository;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("quickTicketService")
public class QuickTicketServiceImpl implements QuickTicketService{

    @Autowired
    public QuickTicketRepository quickTicketRepository;


    @Override
    public List<QuickTicket> findAll() {
        return quickTicketRepository.findAll();
    }

    @Override
    public QuickTicket save(QuickTicket quickTicket) {
        return quickTicketRepository.save(quickTicket);
    }



    @Override
    public QuickTicket saveAndFlush(QuickTicket quickTicket) {
        return quickTicketRepository.saveAndFlush(quickTicket);
    }

    @Override
    public QuickTicket findById(Long id){
        Optional q = quickTicketRepository.findById(id);
        if(q.isPresent()){
            return (QuickTicket)q.get();
        }
        return null;
    }

    @Override
    public List<QuickTicket> findByProjection(Projection projection) {
        return quickTicketRepository.findByProjection(projection);
    }

    @Override
    public List<QuickTicket> findByTheatre(Theatre theatre) {
        return quickTicketRepository.findByTheatre(theatre);
    }


}
