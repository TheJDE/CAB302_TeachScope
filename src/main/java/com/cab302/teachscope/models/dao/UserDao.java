package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.models.entities.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for the User Data Access Object that
 * handles the CRUD operations for our User class
 */
public interface UserDao {

    /**
     * Adds a new user to the database
     * @param user - the user to add
     */
    public void addUser(User user) throws SQLException;

    /**
     * Updates existing user password in the database
     * @param user - the user to update
     */
    public void updateUserPassword(User user) throws SQLException;

    /**
     * Deletes a user from the database
     * @param user - the user to delete
     */
    public void deleteUser(User user) throws SQLException;

    /**
     * Retrieves a specific user from the database
     * @param userName - the user to retrieve
     * @return - the user with the given userName, or null if not found.
     */
    public User getUser(String userName) throws SQLException;

    /**
     * Retrieves all users from the database
     * @return - the user with the given userName, or null if not found.
     */
    public List<User> getAllUsers() throws SQLException;
}
