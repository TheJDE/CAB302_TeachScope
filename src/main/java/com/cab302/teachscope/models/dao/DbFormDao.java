package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DbFormDao implements FormDao {

    private final Connection connection;

    public DbFormDao(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS weekly_forms (" +
                    "id TEXT PRIMARY KEY," +
                    "studentId TEXT NOT NULL," +
                    "term INTEGER NOT NULL," +
                    "week INTEGER NOT NULL," +
                    "attendanceDays INTEGER," +
                    "wasLate BOOLEAN," +
                    "homeworkDone BOOLEAN," +
                    "attentionScore INTEGER," +
                    "literacyScore INTEGER," +
                    "numeracyScore INTEGER," +
                    "understandingScore INTEGER," +
                    "behaviourScore INTEGER," +
                    "createdAt TEXT NOT NULL," +
                    "FOREIGN KEY(studentId) REFERENCES students(id)" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating weekly_forms table", e);
        }
    }

    @Override
    public WeeklyForm create(WeeklyForm form) {
        String sql = "INSERT INTO weekly_forms (id, studentId, term, week, attendanceDays, wasLate, homeworkDone," +
                " attentionScore, literacyScore, numeracyScore, understandingScore, behaviourScore, createdAt) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, form.getId().toString());
            stmt.setString(2, "form.getStudentId()"); ///need to implement get studentId in StudentDao
            stmt.setInt(3, form.getTerm());
            stmt.setInt(4, form.getWeek());
            stmt.setInt(5, form.getAttendanceDays());
            stmt.setBoolean(6, form.isWasLate());
            stmt.setBoolean(7, form.isHomeworkDone());
            stmt.setInt(8, form.getAttentionScore());
            stmt.setInt(9, form.getLiteracyScore());
            stmt.setInt(10, form.getNumeracyScore());
            stmt.setInt(11, form.getUnderstandingScore());
            stmt.setInt(12, form.getBehaviourScore());
            stmt.setString(13, form.getCreatedAt().toString());
            stmt.executeUpdate();
            return form;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting WeeklyForm", e);
        }
    }

    @Override
    public WeeklyForm findById(String id) {
        // TODO: implement fetch by id
        return null;
    }

    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) {
        // TODO: implement fetch by student + range
        return new ArrayList<>();
    }

    @Override
    public List<WeeklyForm> findAll() {
        // TODO: implement fetch all
        return new ArrayList<>();
    }

    @Override
    public void update(WeeklyForm form) {
        // TODO: implement update
    }

    @Override
    public void delete(String id) {
        // TODO: implement delete
    }
}
