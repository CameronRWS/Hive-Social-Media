package com.example.hivefrontend;

public class User {

    private String password, emailAddress;

    public User(String emailAddress, String password)
    {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public String getPassword() { return password; }
}
