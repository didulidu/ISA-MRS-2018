package com.cinemas_theaters.cinemas_theaters.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Offer;
import org.springframework.stereotype.Component;

@Component
public interface OfferRepository  extends JpaRepository<Offer, Long> {
}
