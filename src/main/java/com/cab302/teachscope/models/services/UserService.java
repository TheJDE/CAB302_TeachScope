package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.util.PasswordUtils;
import jakarta.mail.MessagingException;

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
    public void registerUser(String email, String password, String resetCode) throws IllegalArgumentException{
        // Validate user inputs
        // Basic email RE
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }

        // 8 Chars, max 30 Chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char (!@#$%^&*()-_+=)
        if (password == null || !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$")) {
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

        // Hash resetCode
        String resetCodeHash = PasswordUtils.hashResetCode(resetCode);

        // Create and add user
        User user = new User(email, passwordHash, resetCodeHash);

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

    /**
     * Method to generate and send a reset code to the users specified email, successful if no exceptions thrown.
     * @param email The user's email.
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    public void sendPasswordResetCode(String email) throws SQLException, MessagingException {
        User user = userDAO.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("No user found with that email.");
        }

        String resetCode = PasswordUtils.generatePasswordResetCode(6);
        String hashedResetCode = PasswordUtils.hashResetCode(resetCode);

        user.setResetCodeHash(hashedResetCode);
        userDAO.updateUserResetCode(user);

        PasswordUtils.sendResetCode(email, resetCode);
    }

    /**
     * Method to validate the reset code sent to the users email, successful if no exceptions thrown.
     * @param email The user's email.
     * @param resetCode The user's resetCode.
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    public void validateResetCode(String email, String resetCode) throws IllegalArgumentException {
        try {
            User user = userDAO.getUser(email);
            if (resetCode == null) {
                throw new IllegalArgumentException("No resetCode entered");
            }
            if (!user.checkResetCodeMatches(resetCode)) {
                throw new IllegalArgumentException("Incorrect resetCode");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to update the users password, successful if no exceptions thrown.
     * @param email The user's email.
     * @param newPassword The user's new password.
     * @throws IllegalArgumentException If arguments are invalid or if user doesn't exist.
     */
    public void updatePassword(String email, String newPassword) throws IllegalArgumentException {
        if (newPassword == null || !newPassword.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$")) {
            throw new IllegalArgumentException("Invalid password.");
        }
        try {
            User user = userDAO.getUser(email);
            if (user == null) {
                throw new IllegalArgumentException("User not found.");
            }
            String hashedPassword = PasswordUtils.hashPassword(newPassword);
            user.setPasswordHash(hashedPassword);
            userDAO.updateUserPassword(user);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating password", e);
        }
    }

}
