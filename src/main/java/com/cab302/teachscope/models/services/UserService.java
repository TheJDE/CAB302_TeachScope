package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;
import com.cab302.teachscope.util.PasswordUtils;

import java.sql.SQLException;

public class UserService {

    private final UserDao userDAO;

    public UserService(UserDao userDao) {
        this.userDAO = userDao;
    };

    // Public Methods
    public void registerUser(String email, String password) throws IllegalArgumentException{
        // Validate user inputs
        // Basic email RE
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }

        // 8 Chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char (!@#$%^&*()-_+=)
        if (password == null || !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
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

    // Private Methods


}
