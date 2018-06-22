package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Scale;

public interface ScaleService {

    void updateScale(int milestoneSilver, int milestioneGold, double bronzeDiscount, double silverDiscount, double goldDiscount);

    Scale getScale();
}
