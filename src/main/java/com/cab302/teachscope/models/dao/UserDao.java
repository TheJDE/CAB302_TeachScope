package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.models.entities.Users;
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
    public void addUser(Users user);

    /**
     * Updates existing user password in the database
     * @param user - the user to update
     */
    public void updateUserPassword(Users user);

    /**
     * Deletes a user from the database
     * @param user - the user to delete
     */
    public void deleteUser(Users user);

    /**
     * Retrieves a specific user from the database
     * @param userName - the user to retrieve
     * @return - the user with the given userName, or null if not found.
     */
    public Users getUser(String userName);

    /**
     * Retrieves all users from the database
     * @return - the user with the given userName, or null if not found.
     */
    public List<Users> getAllUsers();
}
