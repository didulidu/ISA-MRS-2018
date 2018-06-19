package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegUserProfileUpdateDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserVisitationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Invitation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;
import com.cinemas_theaters.cinemas_theaters.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private InvitationRepository invitationRepository;

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
    @Transactional(readOnly = false)
    public boolean activateUser(RegisteredUser user)
    {
        if(!user.getRegistrationConfirmed()) {
            user.setRegistrationConfirmed(true);
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
        if(alreadyFriends(user, friend))
            return false;
        else {
            Friendship user_friend_friendsip = new Friendship(user, friend, InvitationStatus.Pending);
            Friendship friend_user_friendsip = new Friendship(friend, user, InvitationStatus.Undefined);
            user.getFriendships().add(user_friend_friendsip);
            friend.getFriendships().add(friend_user_friendsip);

            this.registeredUserRepository.save(user);
            this.registeredUserRepository.save(friend);

            return true;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public boolean acceptFriendRequest(RegisteredUser user, RegisteredUser friend){
        if(alreadyFriends(user, friend) || getRegisteredUserFriendship(user, friend).getStatus() != InvitationStatus.Undefined) {
            return false;
        }
        getRegisteredUserFriendship(user, friend).setStatus(InvitationStatus.Accepted);
        getRegisteredUserFriendship(friend, user).setStatus(InvitationStatus.Accepted);

        this.registeredUserRepository.save(user);
        this.registeredUserRepository.save(friend);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteFriendRequest(RegisteredUser user, RegisteredUser friend){
        if(alreadyFriends(user, friend) || getRegisteredUserFriendship(user, friend).getStatus() != InvitationStatus.Undefined) {
            return false;
        }
        Friendship user_friend_friendsip = getRegisteredUserFriendship(user, friend);
        Friendship friend_user_friendsip = getRegisteredUserFriendship(friend, user);

        removeRegisteredUserFriend(user, user_friend_friendsip.getId());
        removeRegisteredUserFriend(friend, friend_user_friendsip.getId());

        this.friendshipRepository.delete(user_friend_friendsip);
        this.friendshipRepository.delete(friend_user_friendsip);
        this.registeredUserRepository.save(user);
        this.registeredUserRepository.save(friend);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean removeFriend(RegisteredUser user, RegisteredUser friend){
        //if(!alreadyFriends(user, friend) || !alreadyFriends(friend, user))
        //    return false;
        //else
        {
            Friendship user_friend_friendsip = getRegisteredUserFriendship(user, friend);
            Friendship friend_user_friendsip = getRegisteredUserFriendship(friend, user);

            removeRegisteredUserFriend(user, user_friend_friendsip.getId());
            removeRegisteredUserFriend(friend, friend_user_friendsip.getId());

            //TODO: Obrisi sve pozivnice od strane obrisanog prijatelja

            this.friendshipRepository.delete(user_friend_friendsip);
            this.friendshipRepository.delete(friend_user_friendsip);
            this.registeredUserRepository.save(user);
            this.registeredUserRepository.save(friend);

            return true;
        }
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
    public List<Reservation> getAllVisitations(RegisteredUser currentUser) {

        List<Reservation> ret = new ArrayList<>();

        for (Reservation reservation: currentUser.getReservations()){
            if (!isReservationOngoing(reservation)){
                ret.add(reservation);
            }
        }

        for(Invitation invitation: currentUser.getInvitations()){
            if (invitation.getStatus() == InvitationStatus.Accepted && !isReservationOngoing(invitation.getReservation())){
                ret.add(invitation.getReservation());
            }
        }

        return ret;
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

    @Override
    public boolean isReservationOngoing(Reservation reservation){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        String nowDateString = sdf.format(now);
        String reservationDateString = reservation.getProjectionDate();
        Date reservationDate = null;
        Date nowDate = null;
        Date thirtyMinutesBeforeReservation = null;
        try {
            nowDate = sdf.parse(nowDateString);
            reservationDate = sdf.parse(reservationDateString);
            thirtyMinutesBeforeReservation = new Date(reservationDate.getTime() - TimeUnit.MINUTES.toMillis(30));

            if(reservationDate.before(nowDate))
                return false;
            //pola sata pre projekcije
            else if (nowDate.after(thirtyMinutesBeforeReservation) && nowDate.before(reservationDate)){
                return false;
            } else{
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList<RegisteredUser> approveInvitations (List<String> invitedUsers, RegisteredUser inviter){
        ArrayList<RegisteredUser> invitedFriends = new ArrayList<>();
        for (String user: invitedUsers){
            RegisteredUser friend = this.registeredUserRepository.findByUsername(user);

            //TODO: provera
            invitedFriends.add(friend);
        }
        return invitedFriends;
    }

    @Override
    public ArrayList<Invitation> sendInvitations(List<RegisteredUser> invitedFriends, RegisteredUser sender, Reservation reservation) {
        ArrayList<Invitation> invitations = new ArrayList<>();
        for (RegisteredUser invitedFriend : invitedFriends) {
            Invitation invitation = new Invitation(InvitationStatus.Pending,invitedFriend, sender, reservation);
            invitations.add(invitation);
            invitedFriend.getInvitations().add(invitation);
        }
        return invitations;
    }

    @Override
    public Invitation checkInvitation(RegisteredUser user, Long invitationId, InvitationStatus status){
        Invitation invite = null;
        for (Invitation inv : user.getInvitations()) {
            if(inv.getId().equals(invitationId) && inv.getStatus().equals(status)){
                invite = inv;
                break;
            }
        }

        return invite;
    }

    @Override
    public List<Invitation> findRegisteredUserInvitations(Long id){
        return this.invitationRepository.findByInvitedUserId(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void acceptInvitation(RegisteredUser user, Invitation invite){
        invite.setStatus(InvitationStatus.Accepted);
        this.registeredUserRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeInvitation(RegisteredUser user, Invitation invite){
        this.invitationRepository.deleteByReservationIdAndInvitationId(user.getId(), invite.getReservation().getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void cancelInvitation(RegisteredUser user, Invitation invite){
        this.invitationRepository.deleteByReservationIdAndInvitationId(user.getId(), invite.getReservation().getId());
    }


    private boolean alreadyFriends(RegisteredUser user, RegisteredUser friends){
        for(Friendship f: user.getFriendships()){
            if (f.getSecondUser().getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    private Friendship getRegisteredUserFriendship(RegisteredUser user, RegisteredUser friend){
        for(Friendship f: user.getFriendships()){
            if (f.getSecondUser().getId().equals(friend.getId())){
                return f;
            }
        }
        return null;
    }

    private boolean removeRegisteredUserFriend(RegisteredUser user, Long id){
        for(Friendship f: user.getFriendships()){
            if (f.getId().equals(id)){
                user.getFriendships().remove(f);
                return true;
            }
        }
        return false;
    }
}
