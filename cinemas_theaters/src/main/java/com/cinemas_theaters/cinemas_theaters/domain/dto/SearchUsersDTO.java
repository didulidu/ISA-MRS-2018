package com.cinemas_theaters.cinemas_theaters.domain.dto;

import java.util.List;

public class SearchUsersDTO {
    List<RegisteredUserSearchDTO> users;
    RegisteredUserDTO currentUser;

    public SearchUsersDTO() { }

    public SearchUsersDTO(List<RegisteredUserSearchDTO> users, RegisteredUserDTO currentUser) {
        this.users = users;
        this.currentUser = currentUser;
    }

    public List<RegisteredUserSearchDTO> getUsers() {
        return users;
    }

    public void setUsers(List<RegisteredUserSearchDTO> users) {
        this.users = users;
    }

    public RegisteredUserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(RegisteredUserDTO currentUser) {
        this.currentUser = currentUser;
    }
}
