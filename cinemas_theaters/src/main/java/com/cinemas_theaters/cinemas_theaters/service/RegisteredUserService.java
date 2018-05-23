package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;

import java.util.List;

public interface RegisteredUserService {
    boolean createNewUser(RegisteredUser user);

    RegisteredUser findByUsername(String username);

    boolean addFriend(RegisteredUser user, RegisteredUser friend);

    boolean acceptFriendRequest(RegisteredUser user, RegisteredUser friend);

    List<UserFriendsDTO> getFriends(Long userId);

    boolean deleteFriendRequest(RegisteredUser user, RegisteredUser friend);

    boolean removeFriend(RegisteredUser user, RegisteredUser friend);

    List<RegisteredUserSearchDTO> findUsers(String username, String parameter);

    List<Ticket> getAllPersonalReservation(RegisteredUser user);
}
