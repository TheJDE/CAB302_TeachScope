package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MockStudentDao implements StudentDao {
    private final Map<String, Student> students = new HashMap<>();

    @Override
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void updateStudent(Student student) {
        students.replace(student.getId(), student);
    }

    @Override
    public void deleteStudent(String Id) {
        students.remove(Id);
    }

    @Override
    public Student getStudent(String id) {
        return students.get(id);
    }

    @Override
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
}




