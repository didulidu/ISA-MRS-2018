package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ReservationInvitationDTO {

    private String invitersUsername;
    private String invitersName;
    private String invitersLastname;
    private String theatreName;
    private String projectionShowTitle;
    private String date;

    public ReservationInvitationDTO() {
    }

    public ReservationInvitationDTO(String invitersUsername, String invitersName, String invitersLastname, String theatreName, String projectionShowTitle, String date) {
        this.invitersUsername = invitersUsername;
        this.invitersName = invitersName;
        this.invitersLastname = invitersLastname;
        this.theatreName = theatreName;
        this.projectionShowTitle = projectionShowTitle;
        this.date = date;
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
}
