package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
