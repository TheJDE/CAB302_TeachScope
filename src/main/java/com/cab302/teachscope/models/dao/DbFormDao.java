package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.DatabaseConnection;
import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.*;
import java.util.*;

/**
 * Database implementation of the Forms data access object
 */
public class DbFormDao implements FormDao {

    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * Constructor
     */
    public DbFormDao() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Creates a new weekly_forms table in the database if it doesn't exist.
     */
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
                    "emotionalState TEXT NOT NULL," +
                    "teacherConcerns TEXT NOT NULL, " +
                    "FOREIGN KEY(studentId) REFERENCES students(id)" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating weekly_forms table", e);
        }
    }

    /**
     * Adds a new form to the database
     * @param form The form to add
     * @throws SQLException On misformed query
     */
    @Override
    public void create(WeeklyForm form) throws SQLException {
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
                "emotionalState, " +
                "teacherConcerns " +
                ")" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        stmt.setString(16, form.getEmotionalState());
        stmt.setString(17, form.getTeacherConcerns());
        stmt.executeUpdate();

    }

    /**
     * Finds a single form from the database
     * @param id ID of the form to find
     * @return The form matching the ID
     * @throws SQLException On misformed query
     */
    @Override
    public WeeklyForm findById(String id) throws SQLException {
        String sql = "SELECT * FROM weekly_forms WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new WeeklyForm(
                        Optional.ofNullable(rs.getString("id")),
                        rs.getString("studentID"),
                        rs.getInt("term"),
                        rs.getInt("week"),
                        rs.getInt("attendanceDays"),
                        rs.getInt("daysLate"),
                        rs.getInt("attentionScore"),
                        rs.getBoolean("homeworkDone"),
                        rs.getInt("participationScore"),
                        rs.getInt("literacyScore"),
                        rs.getInt("numeracyScore"),
                        rs.getInt("understandingScore"),
                        rs.getInt("behaviourScore"),
                        rs.getInt("peerInteractionScore"),
                        rs.getInt("respectForRulesScore"),
                        rs.getString("emotionalState"),
                        rs.getString("teacherConcerns")
                );
            }
        }
        return null;
    }

    /**
     * Finds a list of forms matching a given student and week range
     * @param studentId Student ID to match
     * @param term Term to search
     * @param fromWeek Week to search from, inclusive
     * @param toWeek Week to search to, inclusive
     * @return List of forms
     * @throws SQLException On misformed query
     */
    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) throws SQLException {    List<WeeklyForm> forms = new ArrayList<>();
        String sql = "SELECT * FROM weekly_forms " +
                "WHERE studentId = ? AND term = ? AND week BETWEEN ? AND ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, studentId);
        statement.setInt(2, term);
        statement.setInt(3, fromWeek);
        statement.setInt(4, toWeek);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String ResStudentId = resultSet.getString("studentId");
            int resTerm = resultSet.getInt("term");
            int week = resultSet.getInt("week");
            int attendanceDays = resultSet.getInt("attendanceDays");
            int daysLate = resultSet.getInt("daysLate");
            int attentionScore = resultSet.getInt("attentionScore");
            boolean homeworkDone = resultSet.getBoolean("homeworkDone");
            int participationScore = resultSet.getInt("participationScore");
            int literacyScore = resultSet.getInt("literacyScore");
            int numeracyScore = resultSet.getInt("numeracyScore");
            int understandingScore = resultSet.getInt("understandingScore");
            int behaviourScore = resultSet.getInt("behaviourScore");
            int peerInteractionScore = resultSet.getInt("peerInteractionScore");
            int respectForRulesScore = resultSet.getInt("respectForRulesScore");
            String emotionalState = resultSet.getString("emotionalState");
            String teacherConcerns = resultSet.getString("teacherConcerns");

            WeeklyForm form = new WeeklyForm(
                    Optional.of(id),
                    ResStudentId,
                    resTerm,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );

            forms.add(form);
        }

        return forms;
    }

    /**
     * Finds all forms for given student
     * @param studentId Student ID to match
     * @return List of all forms for student
     * @throws SQLException On misformed query
     */
    @Override
    public List<WeeklyForm> findAllForStudent(String studentId) throws SQLException {
        List<WeeklyForm> forms = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM weekly_forms WHERE studentId = ?");
        statement.setString(1, studentId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String ResStudentId = resultSet.getString("studentId");
            int term = resultSet.getInt("term");
            int week = resultSet.getInt("week");
            int attendanceDays = resultSet.getInt("attendanceDays");
            int daysLate = resultSet.getInt("daysLate");
            int attentionScore = resultSet.getInt("attentionScore");
            boolean homeworkDone = resultSet.getBoolean("homeworkDone");
            int participationScore = resultSet.getInt("participationScore");
            int literacyScore = resultSet.getInt("literacyScore");
            int numeracyScore = resultSet.getInt("numeracyScore");
            int understandingScore = resultSet.getInt("understandingScore");
            int behaviourScore = resultSet.getInt("behaviourScore");
            int peerInteractionScore = resultSet.getInt("peerInteractionScore");
            int respectForRulesScore = resultSet.getInt("respectForRulesScore");
            String emotionalState = resultSet.getString("emotionalState");
            String teacherConcerns = resultSet.getString("teacherConcerns");

            WeeklyForm form = new WeeklyForm(
                    Optional.of(id),
                    ResStudentId,
                    term, week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );

            forms.add(form);
        }

        return forms;
    }

    /**
     * Fetches the average of all score-related fields for a student.
     *
     * @param studentId The student ID
     * @param term The chosen term INT
     * @param fromWeek The starting week of the search INT
     * @param toWeek The end week of the search INT
     * @return A map containing average scores for each field
     * @throws SQLException On database error
     */
    public Map<String, Double> findAverageScoresForStudent(String studentId, int term, int fromWeek, int toWeek) throws SQLException {
        String sql = "SELECT " +
                "AVG(attentionScore) AS attentionScore, " +
                "AVG(participationScore) AS participationScore, " +
                "AVG(literacyScore) AS literacyScore, " +
                "AVG(numeracyScore) AS numeracyScore, " +
                "AVG(understandingScore) AS understandingScore, " +
                "AVG(behaviourScore) AS behaviourScore, " +
                "AVG(peerInteractionScore) AS peerInteractionScore, " +
                "AVG(respectForRulesScore) AS respectForRulesScore " +
                "FROM weekly_forms " +
                "WHERE studentId = ? AND term = ? AND week BETWEEN ? AND ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, studentId);
        statement.setInt(2, term);
        statement.setInt(3, fromWeek);
        statement.setInt(4, toWeek);
        ResultSet rs = statement.executeQuery();

        Map<String, Double> averages = new HashMap<>();
        if (rs.next()) {
            averages.put("attentionScore", rs.getDouble("attentionScore"));
            averages.put("participationScore", rs.getDouble("participationScore"));
            averages.put("literacyScore", rs.getDouble("literacyScore"));
            averages.put("numeracyScore", rs.getDouble("numeracyScore"));
            averages.put("understandingScore", rs.getDouble("understandingScore"));
            averages.put("behaviourScore", rs.getDouble("behaviourScore"));
            averages.put("peerInteractionScore", rs.getDouble("peerInteractionScore"));
            averages.put("respectForRulesScore", rs.getDouble("respectForRulesScore"));
        }

        return averages;
    }

    /**
     * Finds the average of all score fields across all students.
     * @param term The chosen term INT
     * @param fromWeek The starting week of the search INT
     * @param toWeek The end week of the search INT
     * @return Map of average scores for each category.
     * @throws SQLException On query error
     */
    @Override
    public Map<String, Double> findGlobalAverageScores(int term, int fromWeek, int toWeek) throws SQLException {
        String sql = "SELECT " +
                "AVG(attentionScore) AS attentionScore, " +
                "AVG(participationScore) AS participationScore, " +
                "AVG(literacyScore) AS literacyScore, " +
                "AVG(numeracyScore) AS numeracyScore, " +
                "AVG(understandingScore) AS understandingScore, " +
                "AVG(behaviourScore) AS behaviourScore, " +
                "AVG(peerInteractionScore) AS peerInteractionScore, " +
                "AVG(respectForRulesScore) AS respectForRulesScore " +
                "FROM weekly_forms " +
                "WHERE term = ? AND week BETWEEN ? AND ?";

        Map<String, Double> averages = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, term);
            stmt.setInt(2, fromWeek);
            stmt.setInt(3, toWeek);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                averages.put("attentionScore", rs.getDouble("attentionScore"));
                averages.put("participationScore", rs.getDouble("participationScore"));
                averages.put("literacyScore", rs.getDouble("literacyScore"));
                averages.put("numeracyScore", rs.getDouble("numeracyScore"));
                averages.put("understandingScore", rs.getDouble("understandingScore"));
                averages.put("behaviourScore", rs.getDouble("behaviourScore"));
                averages.put("peerInteractionScore", rs.getDouble("peerInteractionScore"));
                averages.put("respectForRulesScore", rs.getDouble("respectForRulesScore"));
            }
        }

        return averages;
    }

    /**
     * Retrieves the average attendance statistics and the most common emotional state
     * for a specific student within a given term and week range.
     *
     * @param studentId the unique ID of the student
     * @param term the term number to query (e.g., 1, 2, 3, 4)
     * @param fromWeek the starting week number of the range
     * @param toWeek the ending week number of the range
     * @return a Map containing data not shown in graph
     */

    public Map<String, Object> findAverageAttendanceAndEmotionForStudent(String studentId, int term, int fromWeek, int toWeek) {
        String sql = """
        SELECT
            AVG(attendanceDays) AS avgAttendanceDays,
            SUM(daysLate) AS totalDaysLate,
            AVG(homeworkDone) AS avgHomeworkDone,
            (
                SELECT emotionalState
                FROM weekly_forms AS wf2
                WHERE wf2.studentId = wf.studentId
                  AND wf2.term = wf.term
                  AND wf2.week BETWEEN ? AND ?
                GROUP BY emotionalState
                ORDER BY COUNT(*) DESC
                LIMIT 1
            ) AS mostCommonEmotionalState
        FROM weekly_forms AS wf
        WHERE wf.studentId = ?
          AND wf.term = ?
          AND wf.week BETWEEN ? AND ?;
    """;

        Map<String, Object> result = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, fromWeek);
            stmt.setInt(2, toWeek);
            stmt.setString(3, studentId);
            stmt.setInt(4, term);
            stmt.setInt(5, fromWeek);
            stmt.setInt(6, toWeek);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result.put("avgAttendanceDays", rs.getDouble("avgAttendanceDays"));
                    result.put("totalDaysLate", rs.getInt("totalDaysLate"));
                    result.put("avgHomeworkDone", rs.getDouble("avgHomeworkDone"));
                    result.put("mostCommonEmotionalState", rs.getString("mostCommonEmotionalState"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gets all forms in the database
     * @return List of all forms
     * @throws SQLException On misformed query
     */

    @Override
    public List<WeeklyForm> findAll() throws SQLException {
        // TODO: implement fetch all
        return new ArrayList<>();
    }

    /**
     * Gets all forms in database for given term/week
     * @param term Term to search
     * @param week Week to search
     * @return List of forms matching week
     * @throws SQLException On misformed query
     */
    @Override
    public List<Map<String, String>> findAllForGivenWeek(int term, int week) throws SQLException {
        List<Map<String, String>> forms = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM weekly_forms WHERE term = ? AND week = ?");
        statement.setInt(1, term);
        statement.setInt(2, week);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Map<String, String> res = new HashMap<>();
            res.put("id", resultSet.getString("id"));
            res.put("studentID", resultSet.getString("studentId"));

            forms.add(res);
        }

        return forms;
    }

    /**
     * Updated a specific form in the database
     * @param form Form to update
     * @throws SQLException On misformed query
     */
    @Override
    public void update(WeeklyForm form) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE weekly_forms SET " +
                "studentId = ?, " +
                "term = ?, " +
                "week = ?, " +
                "attendanceDays = ?," +
                "daysLate = ?, " +
                "attentionScore = ?, " +
                "homeworkDone = ?," +
                "participationScore = ?, " +
                "literacyScore = ?, " +
                "numeracyScore = ?, " +
                "understandingScore = ?, " +
                "behaviourScore = ?, " +
                "peerInteractionScore = ?, " +
                "respectForRulesScore = ?, " +
                "emotionalState = ?, " +
                "teacherConcerns = ? " +
                "WHERE id = ?"
        );

        statement.setString(1, form.getStudentId());
        statement.setInt(2, form.getTerm());
        statement.setInt(3, form.getWeek());
        statement.setInt(4, form.getAttendanceDays());
        statement.setInt(5, form.getDaysLate());
        statement.setInt(6, form.getAttentionScore());
        statement.setBoolean(7, form.isHomeworkDone());
        statement.setInt(8, form.getParticipationScore());
        statement.setInt(9, form.getLiteracyScore());
        statement.setInt(10, form.getNumeracyScore());
        statement.setInt(11, form.getUnderstandingScore());
        statement.setInt(12, form.getBehaviourScore());
        statement.setInt(13, form.getPeerInteractionScore());
        statement.setInt(14, form.getRespectForRulesScore());
        statement.setString(15, form.getEmotionalState());
        statement.setString(16, form.getTeacherConcerns());
        statement.setString(17, form.getId());

        statement.executeUpdate();
    }

    /**
     * Delete a form from the database
     * @param id ID of form to delete
     * @throws SQLException On misformed query
     */
    @Override
    public void delete(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM weekly_forms WHERE id = ?");
        statement.setString(1, id);
        statement.executeUpdate();
    }
}
