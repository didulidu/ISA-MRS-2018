package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.TheaterAdminUser;
import org.springframework.stereotype.Service;

@Service("TCAService")
public interface TheatreCinemaAdminService {
    TheaterAdminUser findByUsername(String username);
    TheaterAdminUser saveAndFlush(TheaterAdminUser user);

}
