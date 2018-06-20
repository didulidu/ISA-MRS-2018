package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.AdminDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.FriendDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheaterAdminUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.service.EmailService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegisteredUserService regUserService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLoginDTO userLoginDTO){
        User user = this.userService.findByEmail(userLoginDTO.getEmail());
        System.out.println("             ----->>>>>>>>"+user.getPassword()+ " " + user.getUsername());
        UserLoginDTO userDTO = new UserLoginDTO(user.getUsername(),user.getPassword(),user.getType(), user.getId(), user.getName(), user.getLastname(), user.getEmail());
        Boolean userExist = this.userService.validateUserCredentials(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if(userExist){
            HttpHeaders headers = new HttpHeaders();
            JwtUser jwtUser = new JwtUser(userDTO.getUsername());
            headers.add("Authorization", this.jwtService.getToken(jwtUser));
            return new ResponseEntity(userDTO,headers,HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(
            value = "/getCurrentUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String userToken) {
        try {
            JwtUser user = this.jwtService.getUser(userToken);
            User currentUser = this.userService.findByUsername(user.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", this.jwtService.getToken(user));

            if (currentUser != null) {
                switch (currentUser.getType()) {
                    case RegisteredUser:
                        RegisteredUser currUser = (RegisteredUser) currentUser;
                        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(currUser.getName(), currUser.getLastname(), currUser.getUsername(), currUser.getEmail(),
                                currUser.getAddress(), currUser.getTelephoneNumber(), currUser.getType(), getRegisteredUserFriends(currUser.getFriendships()));
                        return new ResponseEntity<RegisteredUserDTO>(registeredUserDTO, headers, HttpStatus.OK);
                    //case SystemAdmin:
                    //case TheaterAndCinemaAdmin:
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    private List<FriendDTO> getRegisteredUserFriends(List<Friendship> friendships){
        List<FriendDTO> friendDTOS = new ArrayList<FriendDTO>();
        for (Friendship friendship : friendships) {
            friendDTOS.add(new FriendDTO( friendship.getSecondUser().getUsername(), friendship.getSecondUser().getName(),
                    friendship.getSecondUser().getLastname(), friendship.getStatus()));
        }
        return friendDTOS;
    }

    @PostMapping(
            value = "/admin/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addAdmin(@RequestHeader("Authorization") String token, @RequestBody AdminDTO admin){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user.getType() != UserType.SystemAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        User adminUser = this.userService.addAdmin(admin.getEmail(), admin.getType());
        String adminToken = jwtService.getToken(new JwtUser(adminUser.getUsername()));
        this.emailService.sendAdminActivation((RegisteredUser)adminUser, adminToken);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }




}
