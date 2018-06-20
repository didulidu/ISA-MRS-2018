package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TheatreItemRepository extends JpaRepository<TheatreItem, Long> {

    List<TheatreItem> findAllByActive(Boolean active);
}
