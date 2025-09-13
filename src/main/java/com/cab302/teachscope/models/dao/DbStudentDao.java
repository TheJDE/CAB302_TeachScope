package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DbStudentDao implements StudentDao{
    private Connection connection;

    public DbStudentDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    // Creates Users table
    private void createTable() {
        try {
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "id STRING PRIMARY KEY,"
                    + "fistName STRING NOT NULL,"
                    + "lastName STRING NOT NULL,"
                    + "classCode STRING NOT NULL,"
                    + "gender STRING NOT NULL,"
                    + "enrolmentStatus STRING NOT NULL,"
                    + "gradeLevel STRING NOT NULL,"
                    + ")";

            statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addStudent(Student student) {
        try {
            String query = "INSERT INTO students (id, firstName, lastName, classCode, gender, enrolmentStatus, gradeLevel)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement Statement = connection.prepareStatement(query);
            Statement.executeQuery(query);

            //Set String Fields
            Statement.setString(1, student.getId());
            Statement.setString(2, student.getFirstName());
            Statement.setString(3, student.getLastName());
            Statement.setString(4, student.getClassCode());
            //We cant store ENUM in the database, so we have to convert these ENUM fields to Strings before we then Set them
            Statement.setString(5, student.getGender().name());
            Statement.setString(6, student.getEnrolmentStatus().name());
            Statement.setString(7, student.getGradeLevel().name());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void updateStudentEnrolment(Student student) {
        try{
            PreparedStatement Statement = connection.prepareStatement("UPDATE students SET enrolmentStatus = ? WHERE id = ?");

            Statement.setString(1, student.getEnrolmentStatus().name());
            Statement.setString(2, student.getId());

            Statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteStudent(Student student) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
            statement.setString(1,student.getId());
            statement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudent(String id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String classCode = resultSet.getString("classCode");
                String gender = resultSet.getString("gender");
                String enrolmentStatus = resultSet.getString("enrolmentStatus");
                String gradeLevel = resultSet.getString("gradeLevel");
                // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back
                Student.Gender genderEnum = Student.Gender.valueOf(gender);
                Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);
                Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);

                return new Student(firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String classCode = resultSet.getString("classCode");
                String gender = resultSet.getString("gender");
                String enrolmentStatus = resultSet.getString("enrolmentStatus");
                String gradeLevel = resultSet.getString("gradeLevel");
                // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back
                Student.Gender genderEnum = Student.Gender.valueOf(gender);
                Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);
                Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);

                Student student = new Student(firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
