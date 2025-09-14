package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockUserDao implements UserDao {
    private final Map<String, User> users = new HashMap<>();

    /**
     * Adds users to in-memory storage.
     * @param user - the user to add
     */
    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    /**
     * Updates existing user password.
     * @param user - the user to update
     */
    @Override
    public void updateUserPassword(User user) {

    }

    /**
     * Deletes selected user from in-memory storage.
     * @param user - the user to delete
     */
    @Override
    public void deleteUser(User user) {
        users.remove(user.getEmail());
    }

    /**
     * Gets user from in-memory storage.
     * @param email - the user to retrieve
     * @return Matching user, or null.
     */
    @Override
    public User getUser(String email) {
        return users.get(email);
    }

    /**
     * Gets a list of users from in-memory storage.
     * @return List of user objects.
     */
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
