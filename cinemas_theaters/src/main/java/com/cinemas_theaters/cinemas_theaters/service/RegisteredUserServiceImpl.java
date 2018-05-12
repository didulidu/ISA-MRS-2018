package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.repository.UserRepository;
import com.cinemas_theaters.cinemas_theaters.repository.RegisteredUserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public boolean createNewUser(RegisteredUser user){
        if(this.userRepository.findByUsername(user.getUsername()) == null) {
            this.registeredUserRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public RegisteredUser findByUsername(String username){
        return this.registeredUserRepository.findByUsername(username);
    }
}
