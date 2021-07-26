package com.example.luma.data.model;

public class User {
    private String NameUser,
            LastNameUser,
            EmailUser,
            PasswordUser;

    public User(String NameUser,
                String LastNameUser,
                String EmailUser,
                String PasswordUser){
        this.NameUser = NameUser;
        this.LastNameUser = LastNameUser;
        this.EmailUser = EmailUser;
        this.PasswordUser = PasswordUser;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getLastNameUser() {
        return LastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        LastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setPasswordUser(String passwordUser) {
        PasswordUser = passwordUser;
    }
}