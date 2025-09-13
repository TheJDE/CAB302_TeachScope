package com.cab302.teachscope.models.entities;

import com.cab302.teachscope.util.PasswordUtils;

public class User {
    //Fields
    private String email;
    private String passwordHash;

    //Constructors
    public User(String email, String passwordHash){
        setEmail(email);
        setPasswordHash(passwordHash);
    }

    //Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Public Methods
    public Boolean checkPasswordMatches(String password) {
        return PasswordUtils.hashPassword(password).equals(passwordHash);
    }
}
