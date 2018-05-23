package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class RegisteredUserReservationsDTO {

    private List<Ticket> reservations;

    public RegisteredUserReservationsDTO() { }

    public RegisteredUserReservationsDTO(List<Ticket> personalReservations) {
        this.reservations = personalReservations;
    }

    public List<Ticket> getPersonalReservations() {
        return reservations;
    }

    public void setPersonalReservations(List<Ticket> personalReservations) {
        this.reservations = personalReservations;
    }
}

