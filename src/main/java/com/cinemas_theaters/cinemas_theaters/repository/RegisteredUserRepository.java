package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegisteredUserSearchDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

    RegisteredUser save(RegisteredUser user);

    RegisteredUser findByUsername(String username);

    RegisteredUser findByEmail(String email);

    @Query("SELECT r.name AS name, r.lastname AS lastname, r.username AS username " +
            "FROM RegisteredUser r WHERE r.username <> :username AND lower(concat(r.name, ' ', r.lastname))" +
            "LIKE concat('%', lower(:parameter), '%')")
    List<RegisteredUserSearchDTO> findUsers(@Param("username") String username, @Param("parameter") String parameter);

    @Query("SELECT r.name AS name, r.lastname AS lastname, r.email AS email " +
            "FROM RegisteredUser r WHERE r.email <> :email AND lower(concat(r.name, ' ', r.lastname))" +
            "LIKE concat('%', lower(:parameter), '%')")
    List<RegisteredUserSearchDTO> getUsers(@Param("email") String email, @Param("parameter") String parameter);
}
