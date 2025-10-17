package com.cab302.teachscope.models.entities;

import com.cab302.teachscope.util.IdUtil;

import java.util.Optional;

/**
 * Class representing a student.
 */
public class Student {
    //Fields
    /**
     * Student's ID.
     */
    private String id;

    /**
     * Student's first Name.
     */
    private String firstName;

    /**
     * Student's last Name.
     */
    private String lastName;

    /**
     * Student's class code.
     */
    private String classCode;

    /**
     * Student's gender.
     */
    private Gender gender;

    /**
     * Student's grade level.
     */
    private GradeLevel gradeLevel;

    /**
     * Student's enrolment status
     */
    private EnrolmentStatus enrolmentStatus;

    /**
     * Student's enrolment status
     */
    private String teacher;

    //ENUMs
    /**
     * Enum of gender values.
     */
    public enum Gender {
        Female,
        Male,
        Other
    }

    /**
     * Enum of enrolment values.
     */
    public enum EnrolmentStatus {
        Active,
        Withdrawn
    }

    /**
     * Enum of grade level values.
     */
    public enum GradeLevel {  // We cant have an ENUM option with a SPACE, so we need to have an equivalent string tied to each option
        Prep("Prep"),
        Grade_1("Grade 1"),
        Grade_2("Grade 2"),
        Grade_3("Grade 3"),
        Grade_4("Grade 4"),
        Grade_5("Grade 5"),
        Grade_6("Grade 6");

        // Need to initialise this gradeDisplayName variable
        private final String gradeDisplayName;

        // Setter method to equate the gradeLevel choice to the associated string
        GradeLevel(String gradeDisplayName) {
            this.gradeDisplayName = gradeDisplayName;
        }

        // Getter method to return this gradeDisplayName string.
        public String gradeDisplayName() {
            return gradeDisplayName;
        }
    }

    /**
     * Constructor
     * @param Id Optional student ID. Creates one if not provided.
     * @param firstName Student first name.
     * @param lastName Student last name.
     * @param gender Student gender.
     * @param gradeLevel Student grade level.
     * @param classCode Student class code.
     * @param enrolmentStatus Student enrolment status.
     */
    public Student(Optional<String> Id, String firstName, String lastName, Gender gender, GradeLevel gradeLevel, String classCode, EnrolmentStatus enrolmentStatus, String teacher){
        // If an ID is provided, use that, otherwise generate a new ID
        if (Id.isPresent()) {
             setId(Id.get());
         } else {
             setId(IdUtil.generateIdString());
         }
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setGradeLevel(gradeLevel);
        setClassCode(classCode);
        setEnrolmentStatus(enrolmentStatus);
        setTeacher(teacher);
    }

    //Getters and Setters

    /**
     * Id setter.
     * @param Id Student Id
     */
    public void setId(String Id) {
        this.id = Id;
    }

    /**
     * Id getter.
     * @return Student Id
     */
    public String getId() {
        return id;
    }

    /**
     * First name setter.
     * @param firstName Student first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * First name getter.
     * @return Student first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Last name setter.
     * @param lastName Student last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * last name getter.
     * @return Student last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gender setter.
     * @param gender Student gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gender getter.
     * @return Student gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Grade level setter.
     * @param gradeLevel Student grade level.
     */
    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * Grade level getter.
     * @return Student grade level.
     */
    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    /**
     * Class code setter.
     * @param classCode Student class code.
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     * Class code getter.
     * @return Student class code.
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Enrolment status setter.
     * @param enrolmentStatus Student enrolment status.
     */
    public void setEnrolmentStatus(EnrolmentStatus enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    /**
     * Enrolment status getter.
     * @return Student enrolment status.
     */
    public EnrolmentStatus getEnrolmentStatus() {
        return enrolmentStatus;
    }

    /**
     * Teacher getter.
     * @return Teacher.
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Teacher setter.
     * @param teacher - Students teacher
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}


