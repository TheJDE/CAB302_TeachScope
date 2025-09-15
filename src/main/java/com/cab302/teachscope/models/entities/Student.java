package com.cab302.teachscope.models.entities;

import com.cab302.teachscope.util.IdUtil;

import java.util.Optional;

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
    public Student(Optional<String> Id, String firstName, String lastName, Gender gender, GradeLevel gradeLevel, String classCode, EnrolmentStatus enrolmentStatus){
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
    }

    //Getters and Setters
    public void setId(String Id) {
        this.id = IdUtil.generateIdString();
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


