package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.util.PasswordUtils;

import java.sql.SQLException;

/**
 * Service class for user authentication.
 */
public class UserService {

    /**
     * The specified DAO for the user service.
     */
    private final UserDao userDAO;

    /**
     * Constructor
     * @param userDao The selected user data access object.
     */
    public UserService(UserDao userDao) {
        this.userDAO = userDao;
    };

    // Public Methods

    /**
     * Method to add a new user into the system, successful if no exceptions thrown.
     * @param email The user's email address.
     * @param password The user's password.
     * @throws IllegalArgumentException If arguments are invalid or user exists already.
     */
    public void registerUser(String email, String password) throws IllegalArgumentException{
        // Validate user inputs
        // Password must be 8–30 characters

        // Basic email RE
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (password.length() < 8 || password.length() > 30) {
            throw new IllegalArgumentException("Password must be between 8 and 30 characters");
        }
        // At least one uppercase
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        // At least one lowercase
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        // At least one digit
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
        // At least one special character (!@#$%^&*()-_+=)
        if (!password.matches(".*[@$!%*?&].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character (!@#$%^&*()-_+=)");
        }
        // 8 Chars, max 30 Chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char (!@#$%^&*()-_+=)
        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$")) {
            throw new IllegalArgumentException("Invalid password");
        }
        // Throw error if user already exists
        try {
            if (userDAO.getUser(email) != null) {
                throw new IllegalArgumentException("User already exists in the database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Hash Password
        String passwordHash = PasswordUtils.hashPassword(password);

        // Create and add user
        User user = new User(email, passwordHash);

        try {
            userDAO.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to log in to the system, successful if no exceptions thrown.
     * @param email The user's email.
     * @param password The user's password.
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    public void login(String email, String password) throws IllegalArgumentException {
        try {
            User user = userDAO.getUser(email);

            if (user == null) {
                throw new IllegalArgumentException("User does not exist");
            }

            if (password == null) {
                throw new IllegalArgumentException("No password provided");
            }

            if (!user.checkPasswordMatches(password)) {
                throw new IllegalArgumentException("Incorrect password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
