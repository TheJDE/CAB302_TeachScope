package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.UserDao;
import com.cab302.teachscope.models.entities.User;

import java.util.regex.Pattern;

public class UserService {

    private final UserDao userDAO;

    public UserService(UserDao userDao) {
        this.userDAO = userDao;
    };

    // Public Methods
    public void registerUser(String email, String password) {
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
        if (userDAO.getUser(email) != null) {
            throw new IllegalArgumentException("User already exists in the database");
        }

        // Create and add user
        User user = new User(email, password);

        userDAO.addUser(user);
    }

    // Private Methods


}
