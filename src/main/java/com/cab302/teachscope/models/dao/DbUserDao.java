package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao{
    private Connection connection;

    public DbUserDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
        insertTestLoginData();
    }

    // Creates Users table
    private void createTable() {
        try {
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS users ("
            + "email STRING PRIMARY KEY,"
            + "passWord STRING NOT NULL"
            + ")";

            statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertTestLoginData() {
        try {
            // Clear data from table before inserting to avoid duplicates
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM users";
            clearStatement.execute(clearQuery);
            // Insert the test login data
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INO users (email, passWord) VALUES "
                    + "('testLogin', 'testPassWord')";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addUser(User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPasswordHash());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateUserPassword(User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
            statement.setString(1, user.getPasswordHash());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE email = ?");
            statement.setString(1,user.getEmail());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String email) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return new User(email, password);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(email, password);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
