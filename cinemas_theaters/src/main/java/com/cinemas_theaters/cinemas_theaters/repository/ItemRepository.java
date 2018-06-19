package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> { }
