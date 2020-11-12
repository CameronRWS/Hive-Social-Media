package com.example.hivefrontend;

/**
 * User class
 */
public class User {

    private String password, emailAddress;
    private int id;

    public User(String emailAddress, String password, int id)
    {
        this.emailAddress = emailAddress;
        this.password = password;
        this.id = id;
    }

    /**
     * Returns the email address of the user object
     * @return email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Returns the password of the user object
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Returns the user id of the user object
     * @return the id
     */
    public int getId() {return id;}
}
