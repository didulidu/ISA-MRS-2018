package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class RegUserProfileUpdateDTO {

    private String updatedName;
    private String updatedLastname;
    private String updatedEmail;
    private Boolean passwordChanged;
    private String oldPassword;
    private String updatedPassword1;
    private String updatedPassword2;
    private String updatedTelephoneNumber;
    private String updatedAddress;

    public RegUserProfileUpdateDTO(){

    }

    public RegUserProfileUpdateDTO(String updatedName, String updatedLastname, String updatedEmail, Boolean passwordChanged, String oldPassword, String updatedTelephoneNumber, String updatedAddress) {
        this.updatedName = updatedName;
        this.updatedLastname = updatedLastname;
        this.updatedEmail = updatedEmail;
        this.passwordChanged = passwordChanged;
        this.oldPassword = oldPassword;
        this.updatedTelephoneNumber = updatedTelephoneNumber;
        this.updatedAddress = updatedAddress;
    }

    public RegUserProfileUpdateDTO(String updatedName, String updatedLastname, String updatedEmail, Boolean passwordChanged, String oldPassword, String updatedPassword1, String updatedPassword2, String updatedTelephoneNumber, String updatedAddress) {
        this.updatedName = updatedName;
        this.updatedLastname = updatedLastname;
        this.updatedEmail = updatedEmail;
        this.passwordChanged = passwordChanged;
        this.oldPassword = oldPassword;
        this.updatedPassword1 = updatedPassword1;
        this.updatedPassword2 = updatedPassword2;
        this.updatedTelephoneNumber = updatedTelephoneNumber;
        this.updatedAddress = updatedAddress;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

    public String getUpdatedLastname() {
        return updatedLastname;
    }

    public void setUpdatedLastname(String updatedLastname) {
        this.updatedLastname = updatedLastname;
    }

    public String getUpdatedEmail() {
        return updatedEmail;
    }

    public void setUpdatedEmail(String updatedEmail) {
        this.updatedEmail = updatedEmail;
    }

    public Boolean getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUpdatedPassword1() {
        return updatedPassword1;
    }

    public void setUpdatedPassword1(String updatedPassword1) {
        this.updatedPassword1 = updatedPassword1;
    }

    public String getUpdatedPassword2() {
        return updatedPassword2;
    }

    public void setUpdatedPassword2(String updatedPassword2) {
        this.updatedPassword2 = updatedPassword2;
    }

    public String getUpdatedTelephoneNumber() {
        return updatedTelephoneNumber;
    }

    public void setUpdatedTelephoneNumber(String updatedTelephoneNumber) {
        this.updatedTelephoneNumber = updatedTelephoneNumber;
    }

    public String getUpdatedAddress() {
        return updatedAddress;
    }

    public void setUpdatedAddress(String updatedAddress) {
        this.updatedAddress = updatedAddress;
    }
}
