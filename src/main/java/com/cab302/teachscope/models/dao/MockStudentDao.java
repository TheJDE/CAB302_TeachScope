package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of student data access object.
 */
public class MockStudentDao implements StudentDao {
    /**
     * Mock Database Storage
     */
    private final Map<String, Student> students = new HashMap<>();

    /**
     * Adds student to Map.
     * @param student - the student to add
     */
    @Override
    public void addStudent(Student student, String currentTeacherEmail) {
        students.put(student.getId(), student);
    }

    /**
     * Updates existing student in Map.
     * @param student - the student to update
     */
    @Override
    public void updateStudent(Student student) {
        students.replace(student.getId(), student);
    }

    /**
     * Deletes existing student from Map.
     * @param Id - the students's Id'
     */
    @Override
    public void deleteStudent(String Id) {
        students.remove(Id);
    }

    /**
     * Gets a single student from Map.
     * @param id - the student to retrieve
     * @return Student entity object.
     */
    @Override
    public Student getStudent(String id) {
        return students.get(id);
    }

    /**
     * Gets all students from Map.
     * @return ArrayList of student entity objects.
     */
    @Override
    public ArrayList<Student> getAllStudents(String teacherEmail) {
        return new ArrayList<>(students.values());
    }
}




