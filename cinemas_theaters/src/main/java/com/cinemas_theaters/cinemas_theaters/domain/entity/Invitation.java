package com.cinemas_theaters.cinemas_theaters.domain.entity;

import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private InvitationStatus status;

    @ManyToOne
    private RegisteredUser invitationSender;

    @ManyToOne
    private RegisteredUser invitedUser;

    @ManyToOne
    private Reservation reservation;

    public Invitation() { }

    public Invitation(InvitationStatus status, RegisteredUser invitedUser, RegisteredUser invitationSender, Reservation reservation) {
        this.status = status;
        this.invitedUser = invitedUser;
        this.invitationSender = invitationSender;
        this.reservation = reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public RegisteredUser getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(RegisteredUser invited) {
        this.invitedUser = invited;
    }

    public RegisteredUser getInvitationSender() {
        return invitationSender;
    }

    public void setInvitationSender(RegisteredUser invitationSender) {
        this.invitationSender = invitationSender;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


}
