package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findByInvitedUserId(Long invitedUserId);

    @Modifying
    @Query("DELETE FROM Invitation inv WHERE reservation_reservation_id = :reservation_id")
    void deleteByReservationId(@Param("reservation_id") Long id);

    @Modifying
    @Query("DELETE FROM Invitation inv WHERE id = :id")
    void deleteById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Invitation inv WHERE reservation_reservation_id = :id AND invited_user_id = :invited_user_id")
    void deleteByReservationIdAndInvitationId(@Param("invited_user_id") Long invited_id, @Param("id") Long id);
}
