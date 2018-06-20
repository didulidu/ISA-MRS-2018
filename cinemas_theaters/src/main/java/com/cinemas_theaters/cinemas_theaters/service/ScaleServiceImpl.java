package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Scale;
import com.cinemas_theaters.cinemas_theaters.repository.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scaleService")
public class ScaleServiceImpl implements ScaleService {

    @Autowired
    private ScaleRepository scaleRepository;


    @Override
    public void updateScale(int milestoneSilver, int milestoneGold, double bronzeDiscount,
                            double silverDiscount, double goldDiscount){
        Scale scale = this.scaleRepository.findAll().get(0);
        scale.setMilestoneGold(milestoneGold);
        scale.setMilestoneSilver(milestoneSilver);
        scale.setBronzeDiscount(bronzeDiscount);
        scale.setSilverDiscount(silverDiscount);
        scale.setGoldDiscount(goldDiscount);

        this.scaleRepository.save(scale);
    }

    @Override
    public Scale getScale(){
        return this.scaleRepository.findAll().get(0);
    }
}
