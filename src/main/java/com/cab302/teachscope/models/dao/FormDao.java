package com.cab302.teachscope.models.dao;


import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Interface to create DAOs for student forms.
 */
public interface FormDao {
    /**
     * Creates a new form
     * @param form The form to create
     * @throws SQLException On misformed query
     */
    void create(WeeklyForm form) throws SQLException;

    /**
     * Find a form by ID
     * @param id ID of form to find.
     * @return Form matching ID
     * @throws SQLException On misformed query
     */
    WeeklyForm findById(String id) throws SQLException;

    /**
     * Find list of forms for student within a range
     * @param studentId Student's ID
     * @param term Term to search
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return List of forms for student.
     * @throws SQLException On misformed query
     */
    List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Find average scores for student in range.
     * @param studentId Student's ID
     * @param term Term to search
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Average scores for student within range.
     * @throws SQLException On misformed query
     */
    Map<String, Double> findAverageScoresForStudent(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Find average scores for whole cohort in given range.
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Average scores for cohort within range.
     * @throws SQLException On misformed query
     */
    Map<String, Double> findGlobalAverageScores(int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Find additional student data for range
     * @param studentId Student's ID
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Student's additional data given range.
     * @throws SQLException On misformed query
     */
    Map<String, Object> findAverageAttendanceAndEmotionForStudent(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Retrieves all teacher concerns for student in range
     * @param studentId Student's ID
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Map of teacher concerns for given range.
     * @throws SQLException On misformed query
     */
    Map<String, String> findTeacherConcernsForStudent(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Returns all forms
     * @return List of all forms.
     * @throws SQLException On misformed query
     */
    List<WeeklyForm> findAll() throws SQLException;

    /**
     * Finds all students who have at least one form in given range.
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return List of students.
     * @throws SQLException On misformed query
     */
    List<String> findStudentsInRange(int term, int fromWeek, int toWeek) throws SQLException;

    /**
     * Finds all forms for given student.
     * @param studentId Student's ID
     * @return List of all forms for given student.
     * @throws SQLException On misformed query
     */
    List<WeeklyForm> findAllForStudent(String studentId) throws SQLException;

    /**
     * Find all forms for a given week/term.
     * @param term Term to search.
     * @param week Week to search.
     * @return List of forms matching inputs.
     * @throws SQLException On misformed query
     */
    List<Map<String, String>> findAllForGivenWeek(int term, int week) throws SQLException;

    /**
     * Update an existing form.
     * @param form The form to update.
     * @throws SQLException On misformed query
     */
    void update(WeeklyForm form) throws SQLException;

    /**
     * Delete an existing form.
     * @param id ID of the form to delete.
     * @throws SQLException On misformed query
     */
    void delete(String id) throws SQLException;
}
