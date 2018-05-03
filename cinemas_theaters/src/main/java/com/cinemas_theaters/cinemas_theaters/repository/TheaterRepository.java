package com.cinemas_theaters.cinemas_theaters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Theater;
import org.springframework.stereotype.Component;

@Component
public interface TheaterRepository extends JpaRepository<Theater, Long> { }
