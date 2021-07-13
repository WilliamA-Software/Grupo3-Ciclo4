package com.example.luma.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String name;
    private String lastname;
    private String mail;

    public LoggedInUser(String userId, String name, String lastname, String mail) {
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastname;
    }
    public String getMail() {
        return mail;
    }
}