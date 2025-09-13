package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockUserDao implements UserDao {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public void updateUserPassword(User user) {

    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getEmail());
    }

    @Override
    public User getUser(String email) {
        return users.get(email);
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}

