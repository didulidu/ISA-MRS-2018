package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserRegistrationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/registeredUser")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    //Autowired
    //private JwtService jwtService;

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
