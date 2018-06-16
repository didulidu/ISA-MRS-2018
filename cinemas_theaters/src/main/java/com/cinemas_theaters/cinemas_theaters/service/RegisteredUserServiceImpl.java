package com.cinemas_theaters.cinemas_theaters.service;

import ch.qos.logback.core.CoreConstants;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegUserProfileUpdateDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;
import com.cinemas_theaters.cinemas_theaters.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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

    @Override
    @Transactional(readOnly = false)
    public boolean addFriend(RegisteredUser user, RegisteredUser friend)
    {
        if(user.getFriendships().containsKey(friend))
            return false;
        else {
            Friendship user_friend_friendsip = new Friendship(user, friend, FriendshipStatus.Pending);
            Friendship friend_user_friendsip = new Friendship(friend, user, null);
            user.getFriendships().put(friend, user_friend_friendsip);
            friend.getFriendships().put(user, friend_user_friendsip);

            this.registeredUserRepository.save(user);
            this.registeredUserRepository.save(friend);

            return true;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public boolean acceptFriendRequest(RegisteredUser user, RegisteredUser friend){
        if(!user.getFriendships().containsKey(friend) || user.getFriendships().get(friend).getStatus() != null) {
            return false;
        }
        user.getFriendships().get(friend).setStatus(FriendshipStatus.Accepted);
        friend.getFriendships().get(user).setStatus(FriendshipStatus.Accepted);

        this.registeredUserRepository.save(user);
        this.registeredUserRepository.save(friend);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteFriendRequest(RegisteredUser user, RegisteredUser friend){
        if(!user.getFriendships().containsKey(friend) || user.getFriendships().get(friend).getStatus() != null)
            return false;
        Friendship user_friend_friendsip = user.getFriendships().get(friend);
        Friendship friend_user_friendsip = friend.getFriendships().get(user);

        user.getFriendships().remove(friend);
        friend.getFriendships().remove(user);

        this.friendshipRepository.delete(user_friend_friendsip);
        this.friendshipRepository.delete(friend_user_friendsip);
        this.registeredUserRepository.save(user);
        this.registeredUserRepository.save(friend);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean removeFriend(RegisteredUser user, RegisteredUser friend){
        if(user.getFriendships().containsKey(friend) && friend.getFriendships().containsKey(user)) {
            Friendship user_friend_friendsip = user.getFriendships().get(friend);
            Friendship friend_user_friendsip = friend.getFriendships().get(user);

            user.getFriendships().remove(friend);

            friend.getFriendships().remove(user);

            this.friendshipRepository.delete(user_friend_friendsip);
            this.friendshipRepository.delete(friend_user_friendsip);
            this.registeredUserRepository.save(user);
            this.registeredUserRepository.save(friend);

            return true;
        }
        return false;
    }



    @Override
    @Transactional(readOnly = true)
    public List<UserFriendsDTO> getFriends(Long userId) {

        return this.friendshipRepository.getFriends(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegisteredUserSearchDTO> findUsers(String username, String parameter){
        return this.registeredUserRepository.findUsers(username, parameter);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Reservation> getAllReservations(RegisteredUser user)
    {
        List<Reservation> reservations = new ArrayList<>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateNow = sdf.parse(sdf.format(now));
            long timeNowMinutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime());

            for (Reservation reservation : reservationRepository.findAllReservationsForAUser(user.getId())) {
                Date dateReservation = sdf.parse(reservation.getProjectionDate());
                /*long timeStartReservationMinutes = TimeUnit.MILLISECONDS.toMinutes(reservation.getStartTime().getTime());
                long durationReservationMin = (long)(ticket.getDuration() * 60);
                long timeEndReservationMinutes = timeStartReservationMinutes + durationReservationMin;*/

                reservations.add(reservation);

                if(dateReservation.compareTo(dateNow)>0)
                    // datum rezervacije jos nije dosao
                    //reservations.save(ticket);
                    continue;
                else if(dateReservation.compareTo(dateNow)<0) {
                    /*
                    if(!reservation.getBillCreated()) {
                        // ako nismo oznacili rezervaciju kao zavrsenu, sada to radimo
                        reservation.setBillCreated(true);
                        this.reservationRepository.saveAndFlush(reservation);
                    }*/
                    continue;
                }
                else
                {
                   /*
                    if(timeNowMinutes - timeEndReservationMinutes >= 0) {

                        if(!reservation.getBillCreated())
                        {
                            reservation.setBillCreated(true);
                            this.reservationRepository.saveAndFlush(reservation);
                        }
                        continue;
                    }
                    else
                        // vreme rezervacije jos nije proslo*/
                        reservations.add(reservation);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    @Transactional(readOnly = false)
    public void removeReservation(Reservation reservation){
        this.reservationRepository.delete(reservation);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateRegisteredUserProfile(RegisteredUser user, RegUserProfileUpdateDTO updatedInfo)
    {

        user.setTelephoneNumber(updatedInfo.getUpdatedTelephoneNumber());
        user.setAddress(updatedInfo.getUpdatedAddress());
        user.setName(updatedInfo.getUpdatedName());
        user.setLastname(updatedInfo.getUpdatedLastname());
        user.setEmail(updatedInfo.getUpdatedEmail());

        if (!updatedInfo.getPasswordChanged()) {
            user.setPassword(user.getPassword());
            this.registeredUserRepository.save(user);
            return true;

        }
        else if(updatedInfo.getOldPassword().equals(user.getPassword()) && updatedInfo.getUpdatedPassword1().equals(updatedInfo.getUpdatedPassword2()))
        {
            user.setPassword(updatedInfo.getUpdatedPassword1());
            this.registeredUserRepository.save(user);
            return true;
        }
        else
            return false;
    }
}
