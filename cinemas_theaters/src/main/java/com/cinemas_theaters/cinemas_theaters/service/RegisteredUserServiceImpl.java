package com.cinemas_theaters.cinemas_theaters.service;

import ch.qos.logback.core.CoreConstants;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
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
import java.util.ArrayList;
import java.util.List;

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
            Friendship friendship = new Friendship(user, friend, FriendshipStatus.Pending);
            Friendship inverse = new Friendship(friend, user, null);
            user.getFriendships().put(friend, friendship);
            friend.getFriendships().put(user, inverse);

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
            Friendship friendship = user.getFriendships().get(friend);
            Friendship inverse = friend.getFriendships().get(user);

            user.getFriendships().remove(friend);

            friend.getFriendships().remove(user);

            this.friendshipRepository.delete(friendship);
            this.friendshipRepository.delete(inverse);
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
}
