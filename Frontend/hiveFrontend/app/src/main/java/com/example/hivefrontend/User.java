package com.example.hivefrontend;

public class User {

    private String password, emailAddress;
    private int id;

    public User(String emailAddress, String password, int id)
    {
        this.emailAddress = emailAddress;
        this.password = password;
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public String getPassword() { return password; }
    public int getId() {return id;}
}
