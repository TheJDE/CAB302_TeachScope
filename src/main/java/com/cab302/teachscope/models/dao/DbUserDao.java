package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database implementation of the user data access object.
 */
public class DbUserDao implements UserDao{
    private Connection connection;

    /**
     * Constructor.
     */
    public DbUserDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates a new users table in an SQLite database if it doesn't exist.
     */
    // Creates Users table
    private void createTable() {
        try {
            Statement createtable = connection.createStatement();

            createtable.execute(
            "CREATE TABLE IF NOT EXISTS users ("
                + "email TEXT PRIMARY KEY,"
                + "password TEXT NOT NULL"
                + ")"
            );
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Adds a new user to the database
     * @param user - the user to add
     * @throws SQLException On misformed query.
     */
    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPasswordHash());
        statement.executeUpdate();
    }

    /**
     * Updates existing user password in the database
     * @param user - the user to update
     */
    @Override
    public void updateUserPassword(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
        statement.setString(1, user.getPasswordHash());
        statement.setString(2, user.getEmail());
        statement.executeUpdate();
    }

    /**
     * Deletes a user from the database
     * @param user - the user to delete
     */
    @Override
    public void deleteUser(User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE email = ?");
            statement.setString(1,user.getEmail());
            statement.executeUpdate();
    }

    /**
     * Retrieves a specific user from the database
     * @param email - the user to retrieve
     * @return - the user with the given userName, or null if not found.
     */
    @Override
    public User getUser(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String password = resultSet.getString("password");
            return new User(email, password);
        }

        return null;
    }

    /**
     * Retrieves all users from the database
     * @return - List of user objects.
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM users";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            User user = new User(email, password);
            users.add(user);
        }

        return users;
    }
}
