package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.UserService;
import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserLoginDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegisteredUserService regUserService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLoginDTO userLoginDTO){
        User user = this.userService.findByUsername(userLoginDTO.getUsername());
        UserLoginDTO userDTO = new UserLoginDTO(user.getUsername(),user.getPassword(),user.getType(), user.getId());
        Boolean userExist = this.userService.authenticate(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if(userExist){
            HttpHeaders headers = new HttpHeaders();
            JwtUser jwtUser = new JwtUser(userLoginDTO.getUsername());
            headers.add("Authorization", this.jwtService.getToken(jwtUser));
            return new ResponseEntity(userDTO,headers,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(
            value = "/getCurrentUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserOnSession(@RequestHeader("Authorization") String userToken) {
        JwtUser user = this.jwtService.getUser(userToken);
        User currentUser = this.userService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null) {
            switch (currentUser.getType()){
                case RegisteredUser:
                    return new ResponseEntity<RegisteredUserDTO>(convertRegisteredUserToDTO((RegisteredUser) currentUser), headers, HttpStatus.OK);
                //case SystemAdmin:
                //case TheaterAndCinemaAdmin:
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET)
    public ResponseEntity logout(@RequestHeader("Authorization") String userToken){
        JwtUser user = this.jwtService.getUser(userToken);
        User currentUser = this.userService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    private RegisteredUserDTO convertRegisteredUserToDTO(RegisteredUser user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, RegisteredUserDTO.class);
    }
}
