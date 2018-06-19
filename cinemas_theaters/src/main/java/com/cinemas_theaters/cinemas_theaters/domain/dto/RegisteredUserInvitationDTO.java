package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.enums.InvitationStatus;

public class RegisteredUserInvitationDTO {

    private Long id;
    private Long reservationId;
    private String invitersUsername;
    private String invitersName;
    private String invitersLastname;
    private String theatreName;
    private String projectionShowTitle;
    private String date;
    private InvitationStatus status;

    public RegisteredUserInvitationDTO() {
    }

    public RegisteredUserInvitationDTO(Long id, Long reservationId, String invitersUsername, String invitersName, String invitersLastname, String theatreName, String projectionShowTitle, String date, InvitationStatus status) {
        this.id = id;
        this.reservationId = reservationId;
        this.invitersUsername = invitersUsername;
        this.invitersName = invitersName;
        this.invitersLastname = invitersLastname;
        this.theatreName = theatreName;
        this.projectionShowTitle = projectionShowTitle;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getInvitersUsername() {
        return invitersUsername;
    }

    public void setInvitersUsername(String invitersUsername) {
        this.invitersUsername = invitersUsername;
    }

    public String getInvitersName() {
        return invitersName;
    }

    public void setInvitersName(String invitersName) {
        this.invitersName = invitersName;
    }

    public String getInvitersLastname() {
        return invitersLastname;
    }

    public void setInvitersLastname(String invitersLastname) {
        this.invitersLastname = invitersLastname;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getProjectionShowTitle() {
        return projectionShowTitle;
    }

    public void setProjectionShowTitle(String projectionShowTitle) {
        this.projectionShowTitle = projectionShowTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}

