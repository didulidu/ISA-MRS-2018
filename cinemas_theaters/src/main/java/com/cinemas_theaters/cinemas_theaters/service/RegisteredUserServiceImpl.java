package com.cinemas_theaters.cinemas_theaters.service;

import ch.qos.logback.core.CoreConstants;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.cinemas_theaters.cinemas_theaters.domain.enums.FriendshipStatus;
import com.cinemas_theaters.cinemas_theaters.repository.FriendshipRepository;
import com.cinemas_theaters.cinemas_theaters.repository.UserRepository;
import com.cinemas_theaters.cinemas_theaters.repository.RegisteredUserRepository;


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
    public List<Ticket> getAllPersonalReservation(RegisteredUser user)
    {
        List<Ticket> reservations = new ArrayList<>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateNow = sdf.parse(sdf.format(now));
            long timeNowMinutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime());

            for (Ticket ticket : user.getTickets()) {
                Date dateReservation = sdf.parse(ticket.getProjectionDate());
                /*long timeStartReservationMinutes = TimeUnit.MILLISECONDS.toMinutes(reservation.getStartTime().getTime());
                long durationReservationMin = (long)(ticket.getDuration() * 60);
                long timeEndReservationMinutes = timeStartReservationMinutes + durationReservationMin;*/

                if(dateReservation.compareTo(dateNow)>0)
                    // datum rezervacije jos nije dosao
                    reservations.add(ticket);
                else if(dateReservation.compareTo(dateNow)<0) {
                    /*
                    if(!reservation.getBillCreated()) {
                        // ako nismo oznacili rezervaciju kao zavrsenu, sada to radimo
                        reservation.setBillCreated(true);
                        this.reservationRepository.save(reservation);
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
                            this.reservationRepository.save(reservation);
                        }
                        continue;
                    }
                    else
                        // vreme rezervacije jos nije proslo*/
                        reservations.add(ticket);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
