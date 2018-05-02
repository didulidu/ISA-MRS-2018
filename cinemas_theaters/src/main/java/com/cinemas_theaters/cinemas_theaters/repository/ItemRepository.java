package com.cinemas_theaters.cinemas_theaters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import org.springframework.stereotype.Component;

@Component
public interface ItemRepository extends JpaRepository<Item, Long> {

}
