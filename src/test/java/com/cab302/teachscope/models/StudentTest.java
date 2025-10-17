package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockStudentDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
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
    private String currentTeacherEmail;

    // Student values 2
    private String firstName2;
    private String lastName2;
    private Student.Gender gender2;
    private Student.GradeLevel gradeLevel2;
    private String classCode2;
    private Student.EnrolmentStatus enrolmentStatus2;
    private String currentTeacherEmail2;

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
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertEquals(firstName, student.getFirstName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestAddStudentNullFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(null, lastName, gender, gradeLevel, classCode, enrolmentStatus,currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentNullLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, null, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentNullGender() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, null, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentNullGradeLevel() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, null, classCode, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentNullClassCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, null, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentNullEnrolment() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, null, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentInvalidFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent("@", lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentInvalidLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, "@", gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);
        });
    }

    @Test
    void TestAddStudentInvalidClassCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(firstName, lastName, gender, gradeLevel, "NA", enrolmentStatus, currentTeacherEmail);
        });
    }

    // UPDATING
    @Test
    void TestUpdateStudentValid() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);

            // Get updated student
            Student updatedStudent = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertEquals(firstName2, updatedStudent.getFirstName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullFirstName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), null, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullLastName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, null, gender2, gradeLevel2, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullGender() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, lastName2, null, gradeLevel2, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullGradeLevel() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, null, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullClassCode() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, null, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentNullEnrolment() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, classCode2, null);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentInvalidFirstName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), "@", lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentInvalidLastName() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, "@", gender2, gradeLevel2, classCode2, enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestUpdateStudentInvalidClassCode() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student\
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.updateStudent(student.getId(), firstName2, lastName2, gender2, gradeLevel2, "NA", enrolmentStatus2);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieval
    @Test
    void TestRetrieveValidStudentId() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertEquals(student.getId(), studentService.getStudentById(student.getId()).getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestRetrieveInvalidStudentId() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentById("invalidId");
        });
    }

    @Test
    void TestRetrieveAllStudentsTwoStudents() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);
        studentService.registerStudent(firstName2, lastName2, gender2, gradeLevel2, classCode2, enrolmentStatus2 ,currentTeacherEmail2);

        try {
            List<Student> students = studentDao.getAllStudents(currentTeacherEmail);

            assertEquals(students, studentService.getAllStudents(currentTeacherEmail));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestRetrieveAllStudentsEmpty() {
        try {
            List<Student> students = studentDao.getAllStudents(currentTeacherEmail);

            assertEquals(students, studentService.getAllStudents(currentTeacherEmail));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Test
    void TestDeleteStudentValid() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            studentService.deleteStudent(student.getId());

            assertNull(studentService.getStudentById(student.getId()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestDeleteStudentInvalidId() {
        studentService.registerStudent(firstName, lastName, gender, gradeLevel, classCode, enrolmentStatus, currentTeacherEmail);

        // Get input student
        try {
            Student student = studentDao.getAllStudents(currentTeacherEmail).getFirst();

            assertThrows(IllegalArgumentException.class, () -> {
                studentService.deleteStudent("invalidId");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
