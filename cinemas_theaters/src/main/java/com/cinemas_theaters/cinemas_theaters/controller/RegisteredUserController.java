package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.*;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;
import com.cinemas_theaters.cinemas_theaters.repository.ReservationRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TicketRepository;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.ProjectionService;
import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping(value = "/registeredUser")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectionService projectionService;

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
                        this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
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
                        this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
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
                        this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
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
                        this.template.convertAndSend("/topic/friend" + friendUsername, stringMessage);
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
            value = "/searchUsers/{parameter}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchUsersDTO> searchUsers(@RequestHeader("Authorization") String userToken, @PathVariable String parameter) {
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            List<RegisteredUserSearchDTO> foundUsers = this.registeredUserService.findUsers(currentUser.getUsername(), parameter);
            SearchUsersDTO usersDTO = new SearchUsersDTO(foundUsers, convertRegisteredUserToDTO(currentUser));
            return new ResponseEntity<>(usersDTO, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/getAllRegisteredUserReservations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegisteredUserReservationsDTO> getAllRegisteredUserReservations(@RequestHeader("Authorization") String userToken){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            List<Reservation> reservations = this.registeredUserService.getAllReservations(currentUser);
            return new ResponseEntity<RegisteredUserReservationsDTO>(new RegisteredUserReservationsDTO(reservations), headers, HttpStatus.OK);
        }
        return new ResponseEntity<RegisteredUserReservationsDTO>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/getAllRegisteredUserInvitations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<RegisteredUserInvitationDTO>> getAllRegisteredUserInvitations(@RequestHeader("Authorization") String userToken){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            List<Invitation> invitations = this.registeredUserService.findRegisteredUserInvitations(currentUser.getId());

            List<RegisteredUserInvitationDTO> registeredUserInvitationDTOS = new ArrayList<>();

            for(Invitation invitation: invitations){
                RegisteredUserInvitationDTO registeredUserInvitationDTO = new RegisteredUserInvitationDTO(invitation.getId(), invitation.getReservation().getId(), invitation.getInvitationSender().getUsername(), invitation.getInvitationSender().getName(), invitation.getInvitationSender().getLastname()
                ,invitation.getReservation().getProjection().getShow().getTheatre().getName(), invitation.getReservation().getShowTitle(),invitation.getReservation().getProjectionDate(), invitation.getStatus());
                registeredUserInvitationDTOS.add(registeredUserInvitationDTO);
            }

            return new ResponseEntity<List<RegisteredUserInvitationDTO>>(registeredUserInvitationDTOS, headers, HttpStatus.OK);
        }
        return new ResponseEntity<List<RegisteredUserInvitationDTO>>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/updateDataAndPassword",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserDTO> updateData(@RequestHeader("Authorization") String userToken, @RequestBody RegUserProfileUpdateDTO dataDTO){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null) {
            this.registeredUserService.updateRegisteredUserProfile(currentUser, dataDTO);
            return new ResponseEntity<RegisteredUserDTO>(convertRegisteredUserToDTO(currentUser), headers, HttpStatus.OK);
        }
        return new ResponseEntity<RegisteredUserDTO>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/removeReservation",
            method = RequestMethod.DELETE,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity removeReservation(@RequestHeader("Authorization") String userToken, @RequestBody String reservationId) {
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if (currentUser != null) {
            //Reservation reservation = this.registeredUserService.reservationExist(currentUser, Long.parseLong(reservationId));
            Reservation reservation = reservationRepository.getById(Long.parseLong(reservationId));

            if(reservation != null){
                // otkazivanje rezervacije je moguce samo 30min pre njenog pocetka
                //boolean success = this.registeredUserService.checkReservationExpire(reservation);
                boolean success = true;

                if(success) {
                    /*for (Invitation invited : reservation.getInvites()) {
                        try {
                            this.emailService.sendNotification(currentUser, invited.getInvited(), reservation, reservation.getReservationTables().get(0).getRestaurant());
                        }catch( Exception e ){
                            System.out.println("Greska u slanju e-maila!");
                        }
                    }*/

                    Projection p = this.projectionService.getById(reservation.getProjection().getId());


                    for (Ticket t: reservation.getTickets())
                    {
                        ListIterator<String> it = p.getReservedSeats().listIterator();

                        while(it.hasNext()){
                            if (t.getSeat().getId().equals(Long.parseLong(it.next()))){
                                it.remove();
                            }
                        }

                        this.ticketRepository.delete(t);
                    }

                    this.projectionService.save(p);
                    this.registeredUserService.removeReservation(reservation);
                    return new ResponseEntity(headers, HttpStatus.OK);
                }
                else
                    // preostalo je manje ili tacno 30min do pocetka rezervacije
                    return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
            else
                // rezervacija ne postoji, ili je u pitanju id rezervacije koju je kreirao neko od prijatelja
                return new ResponseEntity(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/acceptInvitation",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ReservationInvitationDTO> acceptInvite(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Pending);
            if(invitation != null) {
                boolean success = this.registeredUserService.hasReservationExpired(invitation.getReservation());

                if(success) {
                    this.registeredUserService.acceptInvitation(currentUser, invitation);

                    Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String date = sdfDate.format(invitation.getReservation().getProjectionDate());
                    ReservationInvitationDTO reservationInvitationDTO = new ReservationInvitationDTO(currentUser.getUsername(),
                            currentUser.getName(), currentUser.getLastname(), theatre.getName(), invitation.getReservation().getShowTitle(), date);

                    return new ResponseEntity(reservationInvitationDTO,headers, HttpStatus.OK);
                }
                else
                    // do pocetka rezervacije je preostalo 30min ili manje
                    return new ResponseEntity(headers, HttpStatus.NOT_ACCEPTABLE);
            }
            else
                // poziv za rucak ne postoji ili je vec prihvacen ranije
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/rejectInvitation",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity ignoreInvite(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Pending);
            if(invitation != null) {
                this.registeredUserService.rejectInvitation(currentUser, invitation);

                Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String date = sdfDate.format(invitation.getReservation().getProjectionDate());
                ReservationInvitationDTO reservationInvitationDTO = new ReservationInvitationDTO(currentUser.getUsername(),
                        currentUser.getName(), currentUser.getLastname(), theatre.getName(), invitation.getReservation().getShowTitle(), date);
                return new ResponseEntity(reservationInvitationDTO,headers, HttpStatus.OK);
            }
            else
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/cancelInvitation",
            method = RequestMethod.DELETE,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity removeInvite(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Accepted);
            if(invitation != null) {
                this.registeredUserService.cancelInvitation(currentUser, invitation);

                Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String date = sdfDate.format(invitation.getReservation().getProjectionDate());
                ReservationInvitationDTO reservationInvitationDTO = new ReservationInvitationDTO(currentUser.getUsername(),
                        currentUser.getName(), currentUser.getLastname(), theatre.getName(), invitation.getReservation().getShowTitle(), date);
                return new ResponseEntity(reservationInvitationDTO,headers, HttpStatus.OK);
            }
            else
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
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
