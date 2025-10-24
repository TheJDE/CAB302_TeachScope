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
    void addUser(User user) throws SQLException;

    /**
     * Updates existing user password in the database
     * @param user - the user to update
     */
    void updateUserPassword(User user) throws SQLException;

    /**
     * Updates existing users hashed reset code in the database
     * @param user - the user to update
     */
    void updateUserResetCode(User user) throws SQLException;

    /**
     * Deletes a user from the database
     * @param user - the user to delete
     */
    void deleteUser(User user) throws SQLException;

    /**
     * Retrieves a specific user from the database
     * @param email - the user to retrieve
     * @return - the user with the given email, or null if not found.
     */
    User getUser(String email) throws SQLException;

    /**
     * Retrieves all users from the database
     * @return - List of user objects.
     */
    List<User> getAllUsers() throws SQLException;
}
