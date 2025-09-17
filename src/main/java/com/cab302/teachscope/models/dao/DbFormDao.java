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
                    "attendanceDays INTEGER NOT NULL," +
                    "daysLate INTEGER NOT NULL," +
                    "attentionScore INTEGER NOT NULL," +
                    "homeworkDone BOOLEAN NOT NULL," +
                    "participationScore INTEGER NOT NULL," +
                    "literacyScore INTEGER NOT NULL," +
                    "numeracyScore INTEGER NOT NULL," +
                    "understandingScore INTEGER NOT NULL," +
                    "behaviourScore INTEGER NOT NULL," +
                    "peerInteractionScore INTEGER NOT NULL," +
                    "respectForRulesScore INTEGER NOT NULL," +
                    "createdAt TEXT NOT NULL," +
                    "FOREIGN KEY(studentId) REFERENCES students(id)" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating weekly_forms table", e);
        }
    }

    @Override
    public WeeklyForm create(WeeklyForm form) throws SQLException {
        String sql = "INSERT INTO weekly_forms (" +
                "id, " +
                "studentId, " +
                "term, " +
                "week, " +
                "attendanceDays," +
                "daysLate, " +
                "attentionScore, " +
                "homeworkDone," +
                "participationScore, " +
                "literacyScore, " +
                "numeracyScore, " +
                "understandingScore, " +
                "behaviourScore, " +
                "peerInteractionScore, " +
                "respectForRulesScore, " +
                "createdAt" +
                ")" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, form.getId());
        stmt.setString(2, form.getStudentId());
        stmt.setInt(3, form.getTerm());
        stmt.setInt(4, form.getWeek());
        stmt.setInt(5, form.getAttendanceDays());
        stmt.setInt(6, form.getDaysLate());
        stmt.setInt(7, form.getAttentionScore());
        stmt.setBoolean(8, form.isHomeworkDone());
        stmt.setInt(9, form.getParticipationScore());
        stmt.setInt(10, form.getLiteracyScore());
        stmt.setInt(11, form.getNumeracyScore());
        stmt.setInt(12, form.getUnderstandingScore());
        stmt.setInt(13, form.getBehaviourScore());
        stmt.setInt(14, form.getPeerInteractionScore());
        stmt.setInt(15, form.getRespectForRulesScore());
        stmt.setString(16, form.getCreatedAt());
        stmt.executeUpdate();

        return form;
    }

    @Override
    public WeeklyForm findById(String id) throws SQLException {
        // TODO: implement fetch by id
        return null;
    }

    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) throws SQLException {
        // TODO: implement fetch by student + range
        return new ArrayList<>();
    }

    @Override
    public List<WeeklyForm> findAll() throws SQLException {
        // TODO: implement fetch all
        return new ArrayList<>();
    }

    @Override
    public void update(WeeklyForm form) throws SQLException {
        // TODO: implement update
    }

    @Override
    public void delete(String id) throws SQLException {
        // TODO: implement delete
    }
}
