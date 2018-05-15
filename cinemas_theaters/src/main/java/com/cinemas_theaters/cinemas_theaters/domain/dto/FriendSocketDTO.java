package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class FriendSocketDTO {
    private String friendName;
    private String friendUsername;
    private String friendLastname;
    private String eventType;

    public FriendSocketDTO() { }

    public FriendSocketDTO(String friendName, String friendUsername, String friendLastname, String eventType) {
        this.friendName = friendName;
        this.friendUsername = friendUsername;
        this.friendLastname = friendLastname;
        this.eventType = eventType;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendLastname() {
        return friendLastname;
    }

    public void setFriendLastname(String friendLastname) {
        this.friendLastname = friendLastname;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}