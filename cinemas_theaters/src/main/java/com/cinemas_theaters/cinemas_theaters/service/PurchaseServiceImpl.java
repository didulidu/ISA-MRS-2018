package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Purchase;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.repository.PurchaseRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public Boolean add(Item item, User user) {
        if (((TheatreItem)item).getQuantity() == 0)
            return false;
        ((TheatreItem)item).setQuantity( ((TheatreItem)item).getQuantity() - 1);
        this.purchaseRepository.save(new Purchase(item, user));
        return true;
    }
}
