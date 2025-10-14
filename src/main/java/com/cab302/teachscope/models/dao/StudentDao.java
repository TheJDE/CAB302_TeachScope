package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Interface for the Student Data Access Object that
 * handles the CRUD operations for our Student class
 */
public interface StudentDao {

    /**
     * Adds a new Student to the database
     * @param student - the user to add
     * @param teacherEmail  - the email of the User that created the student
     */

    void addStudent(Student student, String teacherEmail) throws SQLException;

    /**
     * Updates existing Student Enrolment Status in the database
     * @param student - the student to update
     */
    public void updateStudent(Student student) throws SQLException;

    /**
     * Deletes a student from the database
     * @param Id - the user's Id'
     */
    public void deleteStudent(String Id) throws SQLException;

    /**
     * Retrieves a specific user from the database
     * @param id - the user to retrieve
     * @return - the user with the given userName, or null if not found.
     */
    public Student getStudent(String id) throws SQLException;

    /**
     * Retrieves all users from the database
     * @return - the user with the given userName, or null if not found.
     */
    public List<Student> getAllStudents(String teacherEmail) throws SQLException;
}


