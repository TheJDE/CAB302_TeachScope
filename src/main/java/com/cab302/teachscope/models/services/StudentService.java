package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Pattern;

public class StudentService {

    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    // Fields for id generation
    private static final String NumbSet = "0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int idLength = 4;
    // Generate id using a StringBuilder
    public static String generateId(int idLength) {
        StringBuilder id = new StringBuilder(idLength);
        for (int i = 0; i < idLength; i++) {
            id.append(NumbSet.charAt(random.nextInt(NumbSet.length())));
        }
        return id.toString();
    }


    // Register Student
    public void registerStudent(String firstName, String lastName, Student.Gender gender, Student.GradeLevel gradeLevel, String classCode, Student.EnrolmentStatus enrolmentStatus) {

        // Validate user inputs
        // Make sure that firstName isn't NULL, and that it only contains letters (or a hyphen)
        if (firstName == null || !firstName.matches("^[a-zA-Z-]+$")) {
            throw new IllegalArgumentException("First Name can only contain letters");
        }
        // Make sure that lastName isn't NULL, and that it only contains letters
        if (lastName == null || !lastName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Last Name can only contain letters");
        }
        // Make sure that a gender is selected
        if (gender == null) {
            throw new IllegalArgumentException("Please specify Gender");
        }
        // Make sure that a gradeLevel is selected
        if (gradeLevel == null) {
            throw new IllegalArgumentException("Please specify Grade Level");
        }
        // Make sure that a classCode is specified, and only contains a SINGLE LETTER
        if (classCode == null || !classCode.matches("^[a-zA-Z]$")) {
            throw new IllegalArgumentException("Class Code can only be a single letter");
        }
        // Make sure that a enrolmentStatus is selected
        if (enrolmentStatus == null) {
            throw new IllegalArgumentException("Please specify Enrolment Status");
        }

        // Create and add user
        Student student = new Student(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);
        studentDao.addStudent(student);

        // Generate Student ID after creating the student object.
        student.setId(generateId(idLength));

    }


}
