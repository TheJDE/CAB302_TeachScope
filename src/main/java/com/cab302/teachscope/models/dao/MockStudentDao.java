package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockStudentDao implements StudentDao {
    private final Map<String, Student> students = new HashMap<>();

    @Override
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void updateStudentEnrolment(Student student) {
    }

    @Override
    public void deleteStudent(Student student) {
        students.remove(student.getId(), student);
    }

    @Override
    public Student getStudent(String id) {
        return students.get(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }
}

