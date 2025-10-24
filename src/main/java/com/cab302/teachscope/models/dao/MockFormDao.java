package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock database access object for student forms
 */
public class MockFormDao implements FormDao {

    /**
     * In storage Mock DB
     */
    private final Map<String, WeeklyForm> forms = new HashMap<>();

    /**
     * Creates a new form
     * @param form The form to create
     */
    @Override
    public void create(WeeklyForm form) {
        forms.put(form.getId(), form);
    }

    /**
     * Find a form by ID
     * @param id ID of form to find.
     * @return Form matching ID
     */
    @Override
    public WeeklyForm findById(String id) {
        return forms.get(id);
    }

    /**
     * Find list of forms for student within a range
     * @param studentId Student's ID
     * @param term Term to search
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return List of forms for student.
     */
    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) {
        return List.of();
    }

    /**
     * Find average scores for student in range.
     * @param studentId Student's ID
     * @param term Term to search
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Average scores for student within range.
     */
    @Override
    public Map<String, Double> findAverageScoresForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    /**
     * Find average scores for whole cohort in given range.
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Average scores for cohort within range.
     */
    @Override
    public Map<String, Double> findGlobalAverageScores(int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    /**
     * Find additional student data for range
     * @param studentId Student's ID
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Student's additional data given range.
     */
    @Override
    public Map<String, Object> findAverageAttendanceAndEmotionForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    /**
     * Retrieves all teacher concerns for student in range
     * @param studentId Student's ID
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return Map of teacher concerns for given range.
     */
    @Override
    public Map<String, String> findTeacherConcernsForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return null;
    }

    /**
     * Returns all forms
     * @return List of all forms.
     */
    @Override
    public List<WeeklyForm> findAll() {
        return new ArrayList<>(forms.values());
    }

    /**
     * Finds all students who have at least one form in given range.
     * @param term Term to search.
     * @param fromWeek Week to search from (inclusive).
     * @param toWeek Week to search to (inclusive).
     * @return List of students.
     * @throws SQLException
     */
    public List<String> findStudentsInRange(int term, int fromWeek, int toWeek) throws SQLException {
        return null;
    }

    /**
     * Finds all forms for given student.
     * @param studentId Student's ID
     * @return List of all forms for given student.
     */
    @Override
    public List<WeeklyForm> findAllForStudent(String studentId) {
        return List.of();
    }

    /**
     * Find all forms for a given week/term.
     * @param term Term to search.
     * @param week Week to search.
     * @return List of forms matching inputs.
     */
    @Override
    public List<Map<String, String>> findAllForGivenWeek(int term, int week) {
        List<Map<String, String>> values = new ArrayList<>();

        for (Map.Entry<String, WeeklyForm> item : forms.entrySet()) {
            Map<String, String> res = new HashMap<>();
            res.put("id", item.getValue().getId());
            res.put("studentID", item.getValue().getStudentId());
            values.add(res);
        }

        return values;
    }

    /**
     * Update an existing form.
     * @param form The form to update.
     */
    @Override
    public void update(WeeklyForm form) {
        forms.replace(form.getId(), form);
    }

    /**
     * Delete an existing form.
     * @param id ID of the form to delete.
     */
    @Override
    public void delete(String id) {
        forms.remove(id);
    }
}
