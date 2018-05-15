package com.cinemas_theaters.cinemas_theaters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Hall;

@Component
public interface HallRepository extends JpaRepository<Hall, Long> {
     Hall save(Hall hall);
}
