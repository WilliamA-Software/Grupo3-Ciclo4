package com.example.luma.ui.login;

import androidx.annotation.Nullable;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String name;
    private String lastname;
    private String mail;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String name, String lastname, String mail){
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
    }

    String getName() {
        return name;
    }
    String getLastname() {
        return lastname;
    }
    String getMail() {
        return mail;
    }
}