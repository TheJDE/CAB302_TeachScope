package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockStudentDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private StudentDao studentDao;
    private StudentService studentService;

    // Student values 1
    private String firstName;
    private String lastName;
    private Student.Gender gender;
    private Student.GradeLevel gradeLevel;
    private String classCode;
    private Student.EnrolmentStatus enrolmentStatus;

    // Student values 2
    private String firstName2;
    private String lastName2;
    private Student.Gender gender2;
    private Student.GradeLevel gradeLevel2;
    private String classCode2;
    private Student.EnrolmentStatus enrolmentStatus2;

    @BeforeEach
    void setup() {
        studentDao = new MockStudentDao();
        studentService = new StudentService(studentDao);

        // Student Data (Overwritten values for test cases)
        firstName = "Jane";
        lastName = "Doe";
        gender = Student.Gender.Female;
        gradeLevel = Student.GradeLevel.Grade_2;
        classCode = "A";
        enrolmentStatus = Student.EnrolmentStatus.Active;

        // Second Student Data (Overwritten values for test cases)
        firstName2 = "John";
        lastName2 = "Smith";
        gender2 = Student.Gender.Male;
        gradeLevel2 = Student.GradeLevel.Grade_4;
        classCode2 = "B";
        enrolmentStatus2 = Student.EnrolmentStatus.Withdrawn;
    }

    // ADDING TO DATABASE
    @Test
    void TestAddStudentValid() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        Student student = studentDao.getAllStudents().getFirst();
        assertEquals(firstName, student.getFirstName());
    }

    @Test
    void TestAddStudentNullFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(null, lastName, gender, gradeLevel, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentNullLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, null, gender, gradeLevel, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentNullGender() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, null, gradeLevel, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentNullGradeLevel() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, null, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentNullClassCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, null, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentNullEnrolment() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, null);
        });
    }

    @Test
    void TestAddStudentInvalidFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent("@", lastName, gender, gradeLevel, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentInvalidLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, "@", gender, gradeLevel, classCode, enrolmentStatus);
        });
    }

    @Test
    void TestAddStudentInvalidClassCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, "NA", enrolmentStatus);
        });
    }

    // UPDATING
    @Test
    void TestUpdateStudentValid() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        Student student = studentDao.getAllStudents().getFirst();

        studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);

        // Get updated student
        Student updatedStudent = studentDao.getAllStudents().getFirst();

        assertEquals(firstName2, updatedStudent.getFirstName());
    }

    @Test
    void TestUpdateStudentNullFirstName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), null, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentNullLastName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, null, gender2, gradeLevel2, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentNullGender() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, lastName2, null, gradeLevel2, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentNullGradeLevel() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, null, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentNullClassCode() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, null, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentNullEnrolment() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, classCode2, null);
        });
    }

    @Test
    void TestUpdateStudentInvalidFirstName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), "@", lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentInvalidLastName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, "@", gender2, gradeLevel2, classCode2, enrolmentStatus2);
        });
    }

    @Test
    void TestUpdateStudentInvalidClassCode() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, "NA", enrolmentStatus2);
        });
    }

    // Retrieval
    @Test
    void TestRetrieveValidStudentId() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        Student student = studentDao.getAllStudents().getFirst();

        assertEquals(student.getId(), studentService.getStudentById(student.getId()).getId());
    }

    @Test
    void TestRetrieveInvalidStudentId() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentById("invalidId");
        });
    }

    @Test
    void TestRetrieveAllStudentsTwoStudents() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);
        studentService.registerStudent(firstName2, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);

        List<Student> students = studentDao.getAllStudents();

        assertEquals(students, studentService.getAllStudents());
    }

    @Test
    void TestRetrieveAllStudentsEmpty() {
        List<Student> students = studentDao.getAllStudents();

        assertEquals(students, studentService.getAllStudents());
    }

    // DELETE
    @Test
    void TestDeleteStudentValid() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        studentService.deleteStudent(student.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentById(student.getId());
        });
    }

    @Test
    void TestDeleteStudentInvalidId() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus);

        // Get input student
        Student student = studentDao.getAllStudents().getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.deleteStudent(student.getId());
        });
    }
}
