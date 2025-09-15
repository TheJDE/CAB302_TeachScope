package com.cab302.teachscope.models.dao;
import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbStudentDao implements StudentDao{
    private Connection connection;

    public DbStudentDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    // Creates Users table
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "id TEXT PRIMARY KEY,"
                    + "firstName TEXT NOT NULL,"
                    + "lastName TEXT NOT NULL,"
                    + "classCode TEXT NOT NULL,"
                    + "gender TEXT package com.cab302.teachscope.models.dao;\n" +
                    "import com.cab302.teachscope.DatabaseConnection;\n" +
                    "import com.cab302.teachscope.models.entities.Student;\n" +
                    "\n" +
                    "import java.sql.*;\n" +
                    "import java.util.ArrayList;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Optional;\n" +
                    "\n" +
                    "public class DbStudentDao implements StudentDao{\n" +
                    "    private Connection connection;\n" +
                    "\n" +
                    "    public DbStudentDao() {\n" +
                    "        connection = DatabaseConnection.getInstance();\n" +
                    "        createTable();\n" +
                    "    }\n" +
                    "\n" +
                    "    // Creates Users table\n" +
                    "    private void createTable() {\n" +
                    "        try {\n" +
                    "            Statement statement = connection.createStatement();\n" +
                    "            String query = \"CREATE TABLE IF NOT EXISTS students (\"\n" +
                    "                    + \"id TEXT PRIMARY KEY,\"\n" +
                    "                    + \"fistName TEXT NOT NULL,\"\n" +
                    "                    + \"lastName TEXT NOT NULL,\"\n" +
                    "                    + \"classCode TEXT NOT NULL,\"\n" +
                    "                    + \"gender TEXT NOT NULL,\"\n" +
                    "                    + \"enrolmentStatus TEXT NOT NULL,\"\n" +
                    "                    + \"gradeLevel TEXT NOT NULL\"\n" +
                    "                    + \")\";\n" +
                    "        statement.executeQuery(query);\n" +
                    "        } catch (Exception ex) {\n" +
                    "            System.err.println(ex);\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void addStudent(Student student) throws SQLException {\n" +
                    "        String query = \"INSERT INTO students (id, firstName, lastName, classCode, gender, enrolmentStatus, gradeLevel)\"\n" +
                    "                + \"VALUES (?, ?, ?, ?, ?, ?, ?)\";\n" +
                    "        PreparedStatement Statement = connection.prepareStatement(query);\n" +
                    "        Statement.executeQuery(query);\n" +
                    "        //Set String Fields\n" +
                    "        Statement.setString(1, student.getId());\n" +
                    "        Statement.setString(2, student.getFirstName());\n" +
                    "        Statement.setString(3, student.getLastName());\n" +
                    "        Statement.setString(4, student.getClassCode());\n" +
                    "        //We cant store ENUM in the database, so we have to convert these ENUM fields to Strings before we then Set them\n" +
                    "        Statement.setString(5, student.getGender().name());\n" +
                    "        Statement.setString(6, student.getEnrolmentStatus().name());\n" +
                    "        Statement.setString(7, student.getGradeLevel().name());\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void updateStudent(Student student) throws SQLException {\n" +
                    "        PreparedStatement Statement = connection.prepareStatement(\"UPDATE students SET \" +\n" +
                    "                \"firstName = ?, \" +\n" +
                    "                \"lastName = ?, \" +\n" +
                    "                \"classCode = ?, \" +\n" +
                    "                \"gender = ?, \" +\n" +
                    "                \"enrolmentStatus = ?, \" +\n" +
                    "                \"gradeLevel = ?\" +\n" +
                    "                \"WHERE id = ?\");\n" +
                    "        Statement.setString(1, student.getFirstName());\n" +
                    "        Statement.setString(2, student.getLastName());\n" +
                    "        Statement.setString(3, student.getClassCode());\n" +
                    "        Statement.setString(4, student.getGender().name());\n" +
                    "        Statement.setString(5, student.getEnrolmentStatus().name());\n" +
                    "        Statement.setString(6, student.getGradeLevel().name());\n" +
                    "        Statement.setString(7, student.getId());\n" +
                    "        Statement.executeUpdate();\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void deleteStudent(String Id) throws SQLException {\n" +
                    "        PreparedStatement statement = connection.prepareStatement(\"DELETE FROM students WHERE id = ?\");\n" +
                    "        statement.setString(1, Id);\n" +
                    "        statement.executeUpdate();\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public Student getStudent(String id) throws SQLException {\n" +
                    "        PreparedStatement statement = connection.prepareStatement(\"SELECT * FROM students WHERE id = ?\");\n" +
                    "        statement.setString(1, id);\n" +
                    "        ResultSet resultSet = statement.executeQuery();\n" +
                    "        if (resultSet.next()) {\n" +
                    "            String firstName = resultSet.getString(\"firstName\");\n" +
                    "            String lastName = resultSet.getString(\"lastName\");\n" +
                    "            String classCode = resultSet.getString(\"classCode\");\n" +
                    "            String gender = resultSet.getString(\"gender\");\n" +
                    "            String enrolmentStatus = resultSet.getString(\"enrolmentStatus\");\n" +
                    "            String gradeLevel = resultSet.getString(\"gradeLevel\");\n" +
                    "            // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back\n" +
                    "            Student.Gender genderEnum = Student.Gender.valueOf(gender);\n" +
                    "            Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);\n" +
                    "            Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);\n" +
                    "\n" +
                    "            return new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);\n" +
                    "        }\n" +
                    "\n" +
                    "        return null;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public List<Student> getAllStudents() throws SQLException {\n" +
                    "        List<Student> students = new ArrayList<>();\n" +
                    "\n" +
                    "        Statement statement = connection.createStatement();\n" +
                    "        String query = \"SELECT * FROM students\";\n" +
                    "        ResultSet resultSet = statement.executeQuery(query);\n" +
                    "        while (resultSet.next()) {\n" +
                    "            String id = resultSet.getString(\"id\");\n" +
                    "            String firstName = resultSet.getString(\"firstName\");\n" +
                    "            String lastName = resultSet.getString(\"lastName\");\n" +
                    "            String classCode = resultSet.getString(\"classCode\");\n" +
                    "            String gender = resultSet.getString(\"gender\");\n" +
                    "            String enrolmentStatus = resultSet.getString(\"enrolmentStatus\");\n" +
                    "            String gradeLevel = resultSet.getString(\"gradeLevel\");\n" +
                    "            // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back\n" +
                    "            Student.Gender genderEnum = Student.Gender.valueOf(gender);\n" +
                    "            Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);\n" +
                    "            Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);\n" +
                    "            Student student = new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);\n" +
                    "            students.add(student);\n" +
                    "        }\n" +
                    "\n" +
                    "        return students;\n" +
                    "    }\n" +
                    "}\n" +
                    "\nNOT NULL,"
                    + "enrolmentStatus TEXT NOT NULL,"
                    + "gradeLevel TEXT NOT NULL"
                    + ")";
        statement.executeQuery(query);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (id, firstName, lastName, classCode, gender, enrolmentStatus, gradeLevel)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement Statement = connection.prepareStatement(query);
        Statement.executeQuery(query);
        //Set String Fields
        Statement.setString(1, student.getId());
        Statement.setString(2, student.getFirstName());
        Statement.setString(3, student.getLastName());
        Statement.setString(4, student.getClassCode());
        //We cant store ENUM in the database, so we have to convert these ENUM fields to Strings before we then Set them
        Statement.setString(5, student.getGender().name());
        Statement.setString(6, student.getEnrolmentStatus().name());
        Statement.setString(7, student.getGradeLevel().name());
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        PreparedStatement Statement = connection.prepareStatement("UPDATE students SET " +
                "firstName = ?, " +
                "lastName = ?, " +
                "classCode = ?, " +
                "gender = ?, " +
                "enrolmentStatus = ?, " +
                "gradeLevel = ?" +
                "WHERE id = ?");
        Statement.setString(1, student.getFirstName());
        Statement.setString(2, student.getLastName());
        Statement.setString(3, student.getClassCode());
        Statement.setString(4, student.getGender().name());
        Statement.setString(5, student.getEnrolmentStatus().name());
        Statement.setString(6, student.getGradeLevel().name());
        Statement.setString(7, student.getId());
        Statement.executeUpdate();
    }

    @Override
    public void deleteStudent(String Id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
        statement.setString(1, Id);
        statement.executeUpdate();
    }

    @Override
    public Student getStudent(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String classCode = resultSet.getString("classCode");
            String gender = resultSet.getString("gender");
            String enrolmentStatus = resultSet.getString("enrolmentStatus");
            String gradeLevel = resultSet.getString("gradeLevel");
            // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back
            Student.Gender genderEnum = Student.Gender.valueOf(gender);
            Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);
            Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);

            return new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
        }

        return null;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM students";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String classCode = resultSet.getString("classCode");
            String gender = resultSet.getString("gender");
            String enrolmentStatus = resultSet.getString("enrolmentStatus");
            String gradeLevel = resultSet.getString("gradeLevel");
            // Our Student constructor needs these ENUMs as ENUMs, not as Strings, we need to convert them back
            Student.Gender genderEnum = Student.Gender.valueOf(gender);
            Student.EnrolmentStatus enrolmentStatusEnum = Student.EnrolmentStatus.valueOf(enrolmentStatus);
            Student.GradeLevel gradeLevelEnum = Student.GradeLevel.valueOf(gradeLevel);
            Student student = new Student(Optional.of(id), firstName, lastName, genderEnum, gradeLevelEnum, classCode, enrolmentStatusEnum);
            students.add(student);
        }

        return students;
    }
}



