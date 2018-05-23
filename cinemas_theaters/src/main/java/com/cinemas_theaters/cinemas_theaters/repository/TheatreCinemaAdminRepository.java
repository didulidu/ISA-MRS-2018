package com.cinemas_theaters.cinemas_theaters.repository;


import com.cinemas_theaters.cinemas_theaters.domain.entity.TheaterAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreCinemaAdminRepository extends JpaRepository<TheaterAdminUser,Long> {
    TheaterAdminUser findByUsername(String username);
}
