package com.cab302.teachscope.util;

import com.cab302.teachscope.models.services.StudentService;

public class LoggedInUser {

    /**
     * User's email
     */
    private static String email;

    /**
     * Student service to use
     */
    private static StudentService studentService;

    private LoggedInUser() {} // prevent instantiation

    // ----------------- Email -----------------
    public static void setEmail(String userEmail) { email = userEmail; }
    public static String getEmail() {
        if (email == null) throw new IllegalStateException("No user is currently logged in");
        return email;
    }

    // ----------------- StudentService -----------------
    public static void setStudentService(StudentService service) { studentService = service; }
    public static StudentService getStudentService() {
        if (studentService == null) throw new IllegalStateException("StudentService not initialized");
        return studentService;
    }

    // ----------------- Clear -----------------
    public static void clear() {
        email = null;
        studentService = null;
    }
}