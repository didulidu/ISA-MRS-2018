package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Boolean authenticate(String username, String password){
        User user = findByUsername(username);
        if(user != null){
            if(user.getType().equals(UserType.RegisteredUser)){
                //if(((RegisteredUser) user).getRegistrationConfirmed())
                    return user.getPassword().equals(password);
                    //return false;
            }
            return user.getPassword().equals(password);
        }
        return false;
    }
}
