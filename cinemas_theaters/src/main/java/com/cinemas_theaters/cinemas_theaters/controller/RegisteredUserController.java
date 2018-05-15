package com.cinemas_theaters.cinemas_theaters.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cinemas_theaters.cinemas_theaters.domain.dto.FriendSocketDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserRegistrationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/registeredUser")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping(
            value = "/registration",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guestRegistration(@RequestBody @Valid UserRegistrationDTO newUser, BindingResult result) {
        if(result.hasErrors()){
            // sistemske validacije podataka nisu zadovoljene
            System.out.println(result.getAllErrors());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else {
            if(!newUser.getPassword().equals(newUser.getRepeatedPassword()))
                // unosi za lozinku se ne poklapaju
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            RegisteredUser newRegisteredUser = convertDTOToRegisteredUser(newUser);

            newRegisteredUser.setType(UserType.RegisteredUser);

            newRegisteredUser.setTelephoneNumber("");
            newRegisteredUser.setAddress("");


            boolean userCreated = this.registeredUserService.createNewUser(newRegisteredUser);
            if(!userCreated)
                // vec postoji korisnik sa istim korisnickim imenom
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            else {
                /**try {
                    emailService.sendEmailNotification(newRegisteredUser);
                }catch( Exception e ){
                    System.out.println("Error while when sending an email!");
                    return new ResponseEntity(HttpStatus.CONFLICT);
                }**/
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
    }

    @RequestMapping(
            value = "/addFriend",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> addFriend(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            RegisteredUser friend = this.registeredUserService.findByUsername(friendUsername);

            if(friend != null){
                boolean success = this.registeredUserService.addFriend(currentUser, friend);

                if(success) {
                    FriendSocketDTO message = new FriendSocketDTO(currentUser.getName(), currentUser.getUsername(),
                            currentUser.getLastname(), "addFriend");
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String stringMessage = mapper.writeValueAsString(message);
                        //this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(currentUser, headers, HttpStatus.OK);
                }
                // korisnik koji je ulogovan i korisnik kom se salje zahtev za prijateljstvo
                // su vec prijatelji
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            // dodavanje nepostojeceg korisnika za prijatelja
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/acceptRequest",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> acceptFriendRequest(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            RegisteredUser friend = this.registeredUserService.findByUsername(friendUsername);

            if(friend != null){
                boolean success = this.registeredUserService.acceptFriendRequest(currentUser, friend);

                if(success) {
                    FriendSocketDTO message = new FriendSocketDTO(currentUser.getName(), currentUser.getUsername(),
                            currentUser.getLastname(), "acceptRequest");
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String stringMessage = mapper.writeValueAsString(message);
                        //this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(currentUser, headers, HttpStatus.OK);
                }
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/getFriends",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserFriendsDTO>> getFriends(@RequestHeader("Authorization") String userToken){
        System.out.println(userToken);
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        List<UserFriendsDTO> userFriendsDTOS = new ArrayList<>();

        if(currentUser != null){
            List<UserFriendsDTO> friends = this.registeredUserService.getFriends(currentUser.getId());


            return new ResponseEntity<List<UserFriendsDTO>>(friends, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(
            value = "/deleteFriend",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> deleteFriend(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            RegisteredUser friend = this.registeredUserService.findByUsername(friendUsername);

            if(friend != null){
                boolean success = this.registeredUserService.removeFriend(currentUser, friend);

                if(success) {
                    FriendSocketDTO message = new FriendSocketDTO(currentUser.getName(), currentUser.getUsername(),
                            currentUser.getLastname(), "removeFriend");
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String stringMessage = mapper.writeValueAsString(message);
                        //this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(currentUser, headers, HttpStatus.OK);
                }
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/deleteRequest",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> deleteFriendRequest(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            RegisteredUser friend = this.registeredUserService.findByUsername(friendUsername);

            if(friend != null){
                boolean success = this.registeredUserService.deleteFriendRequest(currentUser, friend);
                if(success) {
                    FriendSocketDTO message = new FriendSocketDTO(currentUser.getName(), currentUser.getUsername(),
                            currentUser.getLastname(), "declineRequest");
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String stringMessage = mapper.writeValueAsString(message);
                        //this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(currentUser, headers, HttpStatus.OK);
                }
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    private RegisteredUserDTO convertRegisteredUserToDTO(RegisteredUser user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, RegisteredUserDTO.class);
    }

    private RegisteredUser convertDTOToRegisteredUser(UserRegistrationDTO userDto)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, RegisteredUser.class);
    }
}
