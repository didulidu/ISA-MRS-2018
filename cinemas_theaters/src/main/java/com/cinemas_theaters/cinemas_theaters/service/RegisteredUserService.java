package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;

public interface RegisteredUserService {
    boolean createNewUser(RegisteredUser user);

    RegisteredUser findByUsername(String username);
}
