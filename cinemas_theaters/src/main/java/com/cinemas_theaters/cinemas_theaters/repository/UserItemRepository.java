package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    List<UserItem> findByStatus(UserItemStatus status);
}
