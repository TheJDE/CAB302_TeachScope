package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockFormDao implements FormDao {

    private final Map<String, WeeklyForm> forms = new HashMap<>();

    @Override
    public void create(WeeklyForm form) {
        forms.put(form.getId(), form);
    }

    @Override
    public WeeklyForm findById(String id) {
        return forms.get(id);
    }

    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) {
        return List.of();
    }

    @Override
    public Map<String, Double> findAverageScoresForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    @Override
    public Map<String, Double> findGlobalAverageScores(int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> findAverageAttendanceAndEmotionForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> findTeacherConcernsForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return null;
    }

    @Override
    public List<WeeklyForm> findAll() {
        return new ArrayList<>(forms.values());
    }

    public List<String> findStudentsInRange(int term, int fromWeek, int toWeek) throws SQLException {
        return null;
    }

    @Override
    public List<WeeklyForm> findAllForStudent(String studentId) {
        return List.of();
    }

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

    @Override
    public void update(WeeklyForm form) {
        forms.replace(form.getId(), form);
    }

    @Override
    public void delete(String id) {
        forms.remove(id);
    }
}
