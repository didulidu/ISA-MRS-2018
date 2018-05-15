package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.dto.UserFriendsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Friendship;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship save(Friendship friendship);

    void delete(Friendship friendship);

    //r.name AS name, r.lastname AS lastname, r.username AS username
    @Query(value = "SELECT r.* " +
            "FROM FRIENDSHIP f JOIN REGISTERED_USER r ON f.second_user_id = r.id " +
            "WHERE f.first_user_id = :id AND f.status = 1", nativeQuery = true)
    List<UserFriendsDTO> getFriends(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Friendship f WHERE first_user_id = :first_id AND second_user_id = :second_id")
    void deleteByFirstAndSecondUser(@Param("first_id") Long first_id, @Param("second_id") Long second_id);

}
