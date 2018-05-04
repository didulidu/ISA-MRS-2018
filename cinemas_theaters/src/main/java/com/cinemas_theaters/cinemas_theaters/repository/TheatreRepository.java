package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre save(Theatre restaurant);

    List<Theatre> findAll();

    Theatre findByName(String name);

    Optional findById(Long id);

}
