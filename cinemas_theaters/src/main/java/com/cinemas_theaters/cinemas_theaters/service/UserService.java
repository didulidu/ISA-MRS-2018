package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;


public interface UserService {

    User findByUsername(String id);

    User findByEmail(String email);

    Boolean validateUserCredentials(String username, String password);

    User addAdmin(String email, UserType type);
}
