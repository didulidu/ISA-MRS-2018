package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class RegisteredUserVisitationDTO {
    String theatreName;
    String showName;
    String projectionDate;

    public RegisteredUserVisitationDTO() {
    }

    public RegisteredUserVisitationDTO(String theatreName, String showName, String projectionDate) {
        this.theatreName = theatreName;
        this.showName = showName;
        this.projectionDate = projectionDate;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(String projectionDate) {
        this.projectionDate = projectionDate;
    }
}
