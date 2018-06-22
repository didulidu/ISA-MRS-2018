package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class RegisteredUserReservationsDTO {

    private List<Reservation> reservations;

    public RegisteredUserReservationsDTO() { }

    public RegisteredUserReservationsDTO(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setPersonalReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}

