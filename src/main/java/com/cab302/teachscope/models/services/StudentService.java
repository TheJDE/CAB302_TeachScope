package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDao studentDAO;
    public StudentService(StudentDao studentDao) { this.studentDAO = studentDao; }

//    // Fields for id generation
//    private static final String NumbSet = "0123456789";
//    private static final SecureRandom random = new SecureRandom();
//    private static final int idLength = 4;
//
//    // Generate id using a StringBuilder
//    // Need to make sure that the id we've just generated, doesn't already exist for another student, so we'll do this in a DO WHILE loop
//    public String generateId(int idLength) {
//        String id;
//        do {
//            StringBuilder id_stringBuilder = new StringBuilder(idLength);
//            for (int i = 0; i < idLength; i++) {
//                id_stringBuilder.append(NumbSet.charAt(random.nextInt(NumbSet.length())));
//            }
//            id = id_stringBuilder.toString();
//
//        } while (studentDAO.getStudent(id) != null);
//        return id;
//    }

    // Register Student
    public void registerStudent (
            String firstName,
            String lastName,
            Student.Gender gender,
            Student.GradeLevel gradeLevel,
            String classCode,
            Student.EnrolmentStatus enrolmentStatus
    ) throws IllegalArgumentException {

        // Validate user inputs
        // Make sure that firstName isn't NULL, and that it only contains letters (or a hyphen)
        if (firstName == null || !firstName.matches("^[a-zA-Z-]+$")) {
            throw new IllegalArgumentException("First Name can only contain letters and hyphens");
        }
        // Make sure that lastName isn't NULL, and that it only contains letters
        if (lastName == null || !lastName.matches("^[a-zA-Z-]+$")) {
            throw new IllegalArgumentException("Last Name can only contain letters and hyphens");
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
        Student student = new Student(Optional.empty(), firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        try {
            studentDAO.addStudent(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent (
            String Id,
            String firstName,
            String lastName,
            Student.Gender gender,
            Student.GradeLevel gradeLevel,
            String classCode,
            Student.EnrolmentStatus enrolmentStatus
    ) throws IllegalArgumentException {
        // Validate user inputs

        // Make sure Id isn't NULL, and is a valid UUID
        if (Id == null || !Id.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Invalid Id");
        }

        // Make sure that firstName isn't NULL, and that it only contains letters (or a hyphen)
        if (firstName == null || !firstName.matches("^[a-zA-Z-]+$")) {
            throw new IllegalArgumentException("First Name can only contain letters and hyphens");
        }
        // Make sure that lastName isn't NULL, and that it only contains letters
        if (lastName == null || !lastName.matches("^[a-zA-Z-]+$")) {
            throw new IllegalArgumentException("Last Name can only contain letters and hyphens");
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

        // Get student object and update values
        Student student = getStudentById(Id);

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGender(gender);
        student.setGradeLevel(gradeLevel);
        student.setClassCode(classCode);
        student.setEnrolmentStatus(enrolmentStatus);

        // Call query
        try {
            studentDAO.updateStudent(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudentById(String Id) {
        if (Id == null || !Id.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Invalid Id");
        }

        try {
            return studentDAO.getStudent(Id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent (String Id) throws IllegalArgumentException {
        if (Id == null || !Id.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Invalid Id");
        }

        try {
            studentDAO.deleteStudent(Id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
