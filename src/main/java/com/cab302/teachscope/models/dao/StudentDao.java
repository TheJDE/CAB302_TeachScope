package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;

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
     */
    public void addStudent(Student student);

    /**
     * Updates existing Student Enrolment Status in the database
     * @param student - the student to update
     */
    public void updateStudentEnrolment(Student student);

    /**
     * Deletes a student from the database
     * @param student - the user to delete
     */
    public void deleteStudent(Student student);

    /**
     * Retrieves a specific user from the database
     * @param id - the user to retrieve
     * @return - the user with the given userName, or null if not found.
     */
    public Student getStudent(UUID id);

    /**
     * Retrieves all users from the database
     * @return - the user with the given userName, or null if not found.
     */
    public List<Student> getAllStudents();
}
