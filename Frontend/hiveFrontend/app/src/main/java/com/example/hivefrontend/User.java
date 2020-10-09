package com.example.hivefrontend;

public class User {

    private int id;
    private String username, emailAddress, displayName, birthday;

    public User(int id, String username, String emailAddress, String displayName, String birthday)
    {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.displayName = displayName;
        this.birthday = birthday;
    }

    public int getId() {return id; }
    public String getDisplayName() {return displayName; };
    public String getBirthday() {return birthday; }
    public String getUsername() {
        return username;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
}
