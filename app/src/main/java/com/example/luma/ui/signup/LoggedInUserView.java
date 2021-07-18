package com.example.luma.ui.signup;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String name;
    private String lastname;
    private String mail;
    private String password;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String name, String lastname, String mail, String password){
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
    }

    String getName() { return name; }
    String getLastname() { return lastname; }
    String getMail() { return mail; }
    String getPassword() { return  password; }
}