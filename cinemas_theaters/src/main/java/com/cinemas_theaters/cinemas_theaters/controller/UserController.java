package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.service.UserService;
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

    //@Autowired
    //private JwtService jwtService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLoginDTO userLoginDTO){
        Boolean userExist = this.userService.authenticate(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if(userExist){
            HttpHeaders headers = new HttpHeaders();

            //JwtUser jwtUser = new JwtUser(userLoginDTO.getUsername());
            //headers.add("Authorization", this.jwtService.getToken(jwtUser));

            return new ResponseEntity(headers, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
