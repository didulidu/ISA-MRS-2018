package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Seat;

import java.util.ArrayList;

public class TicketReservationDTO {

    private String showTitle;

    private String projectionDate;

    private String projectionId;

    private ArrayList<String> invitedFriends;

    private ArrayList<String> seatIds;

    public TicketReservationDTO(){}

    public TicketReservationDTO(String showTitle, String projectionDate, String projectionId, ArrayList<String> invitedFriends, ArrayList<String> seatIds){
        this.showTitle = showTitle;
        this.projectionDate = projectionDate;
        this.projectionId = projectionId;
        this.invitedFriends = invitedFriends;
        this.seatIds = seatIds;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(String projectionDate) {
        this.projectionDate = projectionDate;
    }

    public String getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(String projectionId) {
        this.projectionId = projectionId;
    }

    public ArrayList<String> getInvitedFriends() {
        return invitedFriends;
    }

    public void setInvitedFriends(ArrayList<String> invitedFriends) {
        this.invitedFriends = invitedFriends;
    }

    public ArrayList<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(ArrayList<String> seatIds) {
        this.seatIds = seatIds;
    }
}
