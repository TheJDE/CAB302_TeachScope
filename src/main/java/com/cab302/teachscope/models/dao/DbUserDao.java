package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao{
    private Connection connection;

    public DbUserDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
//        insertTestLoginData();
    }

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

    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPasswordHash());
        statement.executeUpdate();
    }

    @Override
    public void updateUserPassword(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
        statement.setString(1, user.getPasswordHash());
        statement.setString(2, user.getEmail());
        statement.executeUpdate();
    }

    @Override
    public void deleteUser(User user) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE email = ?");
            statement.setString(1,user.getEmail());
            statement.executeUpdate();
    }

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
