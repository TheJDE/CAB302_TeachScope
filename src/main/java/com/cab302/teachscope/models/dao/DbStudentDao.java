package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database implementation of the student data access object.
 */
public class DbStudentDao implements StudentDao{
    private Connection connection;

    /**
     * Constructor
     */
    public DbStudentDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates a new students table in an SQL database if it doesn't exist.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "id TEXT PRIMARY KEY,"
                    + "firstName TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "classCode TEXT NOT NULL,"
                    + "gender TEXT NOT NULL,"
                    + "enrolmentStatus TEXT NOT NULL,"
                    + "gradeLevel TEXT NOT NULL"
                    + ")";
        statement.executeUpdate(query);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Adds a new student to the database.
     * @param student - the student to add
     * @throws SQLException On misformed query.
     */
    @Override
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (id, firstName, lastName, classCode, gender, enrolmentStatus, gradeLevel)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        //Set String Fields
        statement.setString(1, student.getId());
        statement.setString(2, student.getFirstName());
        statement.setString(3, student.getLastName());
        statement.setString(4, student.getClassCode());
        statement.setString(5, student.getGender().name());
        statement.setString(6, student.getEnrolmentStatus().name());
        statement.setString(7, student.getGradeLevel().name());

        statement.executeUpdate();
    }

    /**
     * Updates existing student in the database.
     * @param student - the student to update
     * @throws SQLException On misformed query.
     */
    @Override
    public void updateStudent(Student student) throws SQLException {
        PreparedStatement Statement = connection.prepareStatement("UPDATE students SET " +
                "firstName = ?, " +
                "lastName = ?, " +
                "classCode = ?, " +
                "gender = ?, " +
                "enrolmentStatus = ?, " +
                "gradeLevel = ?" +
                "WHERE id = ?");
        Statement.setString(1, student.getFirstName());
        Statement.setString(2, student.getLastName());
        Statement.setString(3, student.getClassCode());
        Statement.setString(4, student.getGender().name());
        Statement.setString(5, student.getEnrolmentStatus().name());
        Statement.setString(6, student.getGradeLevel().name());
        Statement.setString(7, student.getId());
        Statement.executeUpdate();
    }

    /**
     * Deletes a student from the database.
     * @param Id - the students Id
     * @throws SQLException On misformed query.
     */
    @Override
    public void deleteStudent(String Id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
        statement.setString(1, Id);
        statement.executeUpdate();
    }

    /**
     * Gets a single student from the database.
     * @param id - the student to retrieve
     * @return Student entity object.
     * @throws SQLException On invalid ID.
     */
    @Override
    public Student getStudent(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?");
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

            return new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
        }

        return null;
    }

    /**
     * Gets all students.
     * @return List of student entities.
     * @throws SQLException On misformed query.
     */
    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM students";

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
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
            Student student = new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
            students.add(student);
        }

        return students;
    }
}



