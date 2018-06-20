package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheaterAdminUser;
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
    @Transactional(readOnly = true)
    public User findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Boolean validateUserCredentials(String email, String password){
        User user = findByEmail(email);
        if(user != null){
            if(user.getType().equals(UserType.RegisteredUser)){
                if(((RegisteredUser) user).getRegistrationConfirmed())
                    return user.getPassword().equals(password);
                return false;
            } else if(user.getType().equals((UserType.TheaterAndCinemaAdmin))){
                    return user.getPassword().equals(password);
            }
            return user.getPassword().equals(password);
        }
        return false;
    }
}
