package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.Users;
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
            + "userName STRING PRIMARY KEY,"
            + "passWord STRING NOT NULL"
            + ")";
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
            String insertQuery = "INSERT INO users (userName, passWord) VALUES "
                    + "('testLogin', 'testPassWord')";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addUser(Users user) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (userName, passWord) VALUES (?, ?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassWord());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateUserPassword(Users user) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET passWord = ? WHERE userName = ?");
            statement.setString(1, user.getPassWord());
            statement.setString(2, user.getUserName());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(Users user) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE userName = ?");
            statement.setString(1,user.getUserName());
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Users getUser(String userName) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1,userName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String passWord = resultSet.getString("passWord");
                return new Users(passWord);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        try {
            Statement statment = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statment.executeQuery(query);
            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                String passWord = resultSet.getString("passWord");
                Users user = new Users(passWord);
                user.setUserName(userName);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
