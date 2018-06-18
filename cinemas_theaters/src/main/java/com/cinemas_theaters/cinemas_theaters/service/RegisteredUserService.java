package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegUserProfileUpdateDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Invitation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;

import java.util.ArrayList;
import java.util.List;

public interface RegisteredUserService {
    boolean createNewUser(RegisteredUser user);


    boolean activateUser(RegisteredUser user);

    RegisteredUser findByUsername(String username);

    boolean addFriend(RegisteredUser user, RegisteredUser friend);

    boolean acceptFriendRequest(RegisteredUser user, RegisteredUser friend);

    List<UserFriendsDTO> getFriends(Long userId);

    boolean deleteFriendRequest(RegisteredUser user, RegisteredUser friend);

    boolean removeFriend(RegisteredUser user, RegisteredUser friend);

    List<RegisteredUserSearchDTO> findUsers(String username, String parameter);

    List<Reservation> getAllReservations(RegisteredUser user);

    void removeReservation(Reservation reservation);

    boolean hasReservationExpired(Reservation reservation);

    boolean updateRegisteredUserProfile(RegisteredUser user, RegUserProfileUpdateDTO updatedInfo);

    ArrayList<RegisteredUser> approveInvitations(List<String> invitedUsers, RegisteredUser inviter);

    ArrayList<Invitation> sendInvitations(List<RegisteredUser> invitedFriends, RegisteredUser sender, Reservation reservation);

    Invitation checkInvitation(RegisteredUser user, Long invitationId, InvitationStatus invitationStatus);

    void acceptInvitation(RegisteredUser user, Invitation invitation);

    void rejectInvitation(RegisteredUser user, Invitation invitation);

    void cancelInvitation(RegisteredUser user, Invitation invitation);

    List<Invitation> findRegisteredUserInvitations(Long id);
}
