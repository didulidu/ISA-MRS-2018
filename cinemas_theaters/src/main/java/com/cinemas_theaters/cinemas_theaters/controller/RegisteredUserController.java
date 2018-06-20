package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.*;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;
import com.cinemas_theaters.cinemas_theaters.domain.enums.MembershipStatus;
import com.cinemas_theaters.cinemas_theaters.repository.ReservationRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TicketRepository;
import com.cinemas_theaters.cinemas_theaters.service.*;
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

import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TheatreCinemaAdminService theatreCinemaAdminService;

    @RequestMapping(
            value = "/activateUser",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity activateUser(@RequestBody String token)
    {
        JwtUser user = this.jwtService.getUser(token);
        RegisteredUser registeredUser = this.registeredUserService.findByUsername(user.getUsername());

        if(registeredUser != null) {
            boolean activationCompleted = this.registeredUserService.activateUser(registeredUser);

            if (activationCompleted)
                return new ResponseEntity(HttpStatus.OK);
            else
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(
            value = "/registration",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@RequestBody @Valid UserRegistrationDTO newUser, BindingResult result) {
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else {
            if(!newUser.getPassword().equals(newUser.getRepeatedPassword()))
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            RegisteredUser newRegisteredUser = convertDTOToRegisteredUser(newUser);

            newRegisteredUser.setType(UserType.RegisteredUser);
            newRegisteredUser.setTelephoneNumber("");
            newRegisteredUser.setAddress("");

            boolean userCreated = this.registeredUserService.createNewUser(newRegisteredUser);
            if(!userCreated)
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            else {
                String token = jwtService.getToken(new JwtUser(newUser.getUsername()));
                try {
                    emailService.sendUserActivation(newRegisteredUser, token);
                }catch( Exception e ){
                    e.printStackTrace();
                    return new ResponseEntity(HttpStatus.CONFLICT);
                }
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
    }

    @RequestMapping(
            value = "/addFriend",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserDTO> addFriend(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
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
                    RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(currentUser.getName(),currentUser.getLastname(), currentUser.getUsername(), currentUser.getEmail(),
                            currentUser.getAddress(), currentUser.getTelephoneNumber(), currentUser.getType(), getRegisteredUserFriends(currentUser.getFriendships()));
                    return new ResponseEntity<RegisteredUserDTO>(registeredUserDTO, headers, HttpStatus.OK);
                }
                // korisnik koji je ulogovan i korisnik kom se salje zahtev za prijateljstvo
                // su vec prijatelji
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/acceptRequest",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserDTO> acceptFriendRequest(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
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
                    RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(currentUser.getName(),currentUser.getLastname(), currentUser.getUsername(), currentUser.getEmail(),
                            currentUser.getAddress(), currentUser.getTelephoneNumber(), currentUser.getType(), getRegisteredUserFriends(currentUser.getFriendships()));
                    return new ResponseEntity<RegisteredUserDTO>(registeredUserDTO, headers, HttpStatus.OK);
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
    public ResponseEntity<RegisteredUserDTO> deleteFriend(@RequestHeader("Authorization") String userToken, @RequestBody String friendUsername){
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
                    RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(currentUser.getName(),currentUser.getLastname(), currentUser.getUsername(), currentUser.getEmail(),
                            currentUser.getAddress(), currentUser.getTelephoneNumber(), currentUser.getType(), getRegisteredUserFriends(currentUser.getFriendships()));
                    return new ResponseEntity<>(registeredUserDTO, headers, HttpStatus.OK);
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
            RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(currentUser.getName(),currentUser.getLastname(), currentUser.getUsername(), currentUser.getEmail(),
                    currentUser.getAddress(), currentUser.getTelephoneNumber(), currentUser.getType(), getRegisteredUserFriends(currentUser.getFriendships()));
            SearchUsersDTO usersDTO = new SearchUsersDTO(foundUsers, registeredUserDTO);
            return new ResponseEntity<>(usersDTO, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    private List<FriendDTO> getRegisteredUserFriends(List<Friendship> friendships){
        List<FriendDTO> friendDTOS = new ArrayList<FriendDTO>();
        for (Friendship friendship : friendships) {
            friendDTOS.add(new FriendDTO( friendship.getSecondUser().getUsername(), friendship.getSecondUser().getName(),
                    friendship.getSecondUser().getLastname(), friendship.getStatus()));
        }
        return friendDTOS;
    }

    @RequestMapping(
            value = "/getAllRegisteredUserVisitations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<RegisteredUserVisitationDTO>> getAllRegisteredUserVisitations(@RequestHeader("Authorization") String userToken){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            List<Reservation> userReservations = this.registeredUserService.getAllVisitations(currentUser);
            List<RegisteredUserVisitationDTO> registeredUserVisitationDTOS = new ArrayList<>();

            for(Reservation r: userReservations){
                registeredUserVisitationDTOS.add(new RegisteredUserVisitationDTO(r.getProjection().getShow().getTheatre().getName(), r.getProjection().getShow().getTitle(),
                        r.getProjection().getDate()));
            }

            return new ResponseEntity<List<RegisteredUserVisitationDTO>>(registeredUserVisitationDTOS, headers, HttpStatus.OK);
        }
        return new ResponseEntity<List<RegisteredUserVisitationDTO>>(headers, HttpStatus.UNAUTHORIZED);
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
            value = "/updateRegisteredUserData",
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
            Reservation reservation = ticketService.getReservation(Long.parseLong(reservationId));

            if(reservation != null){
                boolean notTooLate = registeredUserService.isReservationOngoing(reservation);

                if(notTooLate) {
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

                    for(Reservation r: p.getReservations()){
                        if (r.getId().equals(reservation.getId())){
                            p.getReservations().remove(r);
                            break;
                        }
                    }

                    this.projectionService.save(p);
                    this.registeredUserService.removeReservation(reservation);

                    currentUser.setPoints(currentUser.getPoints()-1);
                    currentUser.setMembershipStatus(updateUserMembership(currentUser));

                    this.registeredUserService.save(currentUser);

                    return new ResponseEntity(headers, HttpStatus.OK);
                }
                else
                    return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
            }
            else
                return new ResponseEntity(headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    private MembershipStatus updateUserMembership(RegisteredUser currentUser){
        if (currentUser.getPoints() < 0){
            currentUser.setPoints(0);
            return MembershipStatus.Bronze;
        }

        if(currentUser.getPoints()>=20){
            return MembershipStatus.Gold;
        } else if(currentUser.getPoints()>=10){
            return MembershipStatus.Silver;
        } else{
            return MembershipStatus.Bronze;
        }
    }

    @RequestMapping(
            value = "/acceptInvitation",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ReservationInvitationDTO> acceptInvitation(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Pending);
            if(invitation != null) {
                boolean success = this.registeredUserService.isReservationOngoing(invitation.getReservation());

                if(success) {
                    this.registeredUserService.acceptInvitation(currentUser, invitation);

                    Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                    String date = invitation.getReservation().getProjectionDate();
                    ReservationInvitationDTO reservationInvitationDTO = new ReservationInvitationDTO(currentUser.getUsername(),
                            currentUser.getName(), currentUser.getLastname(), theatre.getName(), invitation.getReservation().getShowTitle(), date);

                    return new ResponseEntity<>(reservationInvitationDTO,headers, HttpStatus.OK);
                }
                else
                    return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
            }
            else
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(
            value = "/cancelInvitation",
            method = RequestMethod.DELETE,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity cancelInvitation(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Pending);
            if(invitation != null) {
                this.registeredUserService.cancelInvitation(currentUser, invitation);

                Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                String date = invitation.getReservation().getProjectionDate();
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
            value = "/removeInvitation",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity removeInvitation(@RequestHeader("Authorization") String userToken, @RequestBody String idInvite){
        JwtUser user = this.jwtService.getUser(userToken);
        RegisteredUser currentUser = this.registeredUserService.findByUsername(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", this.jwtService.getToken(user));

        if(currentUser != null){
            Invitation invitation = this.registeredUserService.checkInvitation(currentUser, Long.parseLong(idInvite), InvitationStatus.Accepted);
            if(invitation != null) {
                this.registeredUserService.removeInvitation(currentUser, invitation);

                Theatre theatre = invitation.getReservation().getProjection().getShow().getTheatre();
                String date = invitation.getReservation().getProjectionDate();
                ReservationInvitationDTO reservationInvitationDTO = new ReservationInvitationDTO(currentUser.getUsername(),
                        currentUser.getName(), currentUser.getLastname(), theatre.getName(), invitation.getReservation().getShowTitle(), date);
                return new ResponseEntity(reservationInvitationDTO,headers, HttpStatus.OK);
            }
            else
                return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(
            value = "/admin-edit/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity editAdminInfo(@RequestHeader("Authorization") String userToken, @RequestBody @Valid UserLoginDTO userDTO, @PathVariable("id") String id, BindingResult result ) {
        String username = this.jwtService.getUser(userToken).getUsername();
        TheaterAdminUser user = this.theatreCinemaAdminService.findByUsername(username);
        if (!user.getType().equals(UserType.TheaterAndCinemaAdmin)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        System.out.println("EMAIL ------->>"+user.getEmail());


        if(!id.equals(user.getId().toString())){
            System.out.println("NE GLEDAJ U TUDJ TANJIR");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {

            user.setName(userDTO.getName());
            user.setLastname(userDTO.getLastname());
            user.setPassword(userDTO.getPassword());

            theatreCinemaAdminService.saveAndFlush(user);
            return new ResponseEntity<UserLoginDTO>(userDTO, HttpStatus.CREATED);
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
