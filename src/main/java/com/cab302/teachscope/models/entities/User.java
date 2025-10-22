package com.cab302.teachscope.models.entities;

import com.cab302.teachscope.util.PasswordUtils;

/**
 * Application user entity.
 */
public class User {
    //Fields
    /**
     * User's email.
     */
    private String email;

    /**
     * User's hashed password.
     */
    private String passwordHash;

    /**
     * User's hashed password reset code.
     */
    private String resetCodeHash;

    /**
     * Constructor.
     * @param email The user's email.
     * @param passwordHash The user's hashed password.
     */
    public User(String email, String passwordHash, String resetCodeHash){
        setEmail(email);
        setPasswordHash(passwordHash);
        setResetCodeHash(resetCodeHash);
    }

    //Getters and Setters

    /**
     * Email getter.
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter.
     * @param email The user's email address.
     */
    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        this.email = email;
    }

    /**
     * Password hash getter.
     * @return The user's hashed password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Password hash setter.
     * @param passwordHash The user's hashed password.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * resetCode hash getter.
     * @return The user's hashed reset code.
     */
    public String getResetCodeHash() {
        return resetCodeHash;
    }

    /**
     * resetCode hash setter.
     * @param resetCodeHash The user's hashed reset code.
     */
    public void setResetCodeHash(String resetCodeHash) {
        this.resetCodeHash = resetCodeHash;
    }



    // Public Methods

    /**
     * Checks if given password matches the user's hashed password after hashing.
     * @param password The password to check.
     * @return Matches: True, Doesn't Match: False
     */
    public Boolean checkPasswordMatches(String password) {
        return PasswordUtils.hashPassword(password).equals(passwordHash);
    }

    /**
     * Checks if given reset code matches the user's hashed reset code after hashing.
     * @param resetCode The password to check.
     * @return Matches: True, Doesn't Match: False
     */
    public Boolean checkResetCodeMatches(String resetCode) {
        return PasswordUtils.hashResetCode(resetCode).equals(resetCodeHash);
    }
}


