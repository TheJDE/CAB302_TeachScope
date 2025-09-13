package com.cab302.teachscope.models.entities;

import java.util.UUID;

public class Student {
    //Fields
    private String id;
    private String firstName;
    private String lastName;
    private String classCode;
    // Field for Gender ENUM type, and then ENUM
    private Gender gender;
    public enum Gender {
        Female,
        Male }
    // Field for EnrolmentStatus ENUM type, and then ENUM
    private EnrolmentStatus enrolmentStatus;
    public enum EnrolmentStatus {
        Active,
        Withdrawn}
    // Field for GradeLevel ENUM type, and then ENUM
    private GradeLevel gradeLevel;
    public enum GradeLevel {
        Prep("Prep"),
        Grade_1("Grade 1"),
        Grade_2("Grade 2"),
        Grade_3("Grade 3"),
        Grade_4("Grade 4"),
        Grade_5("Grade 5"),
        Grade_6("Grade 6");
        // We cant have an ENUM option with a SPACE, so we need to have an equivalent string tied to each option
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

    //Constructors
    public Student(String firstName, String lastName, Gender gender, GradeLevel gradeLevel, String classCode, EnrolmentStatus enrolmentStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
        this.classCode = classCode;
        this.enrolmentStatus = enrolmentStatus;
    }

    //Getters and Setters
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    public String getClassCode() {
        return classCode;
    }

    public void setEnrolmentStatus(EnrolmentStatus enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }
    public EnrolmentStatus getEnrolmentStatus() {
        return enrolmentStatus;
    }

}


