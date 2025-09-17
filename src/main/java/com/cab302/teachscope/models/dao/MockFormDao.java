package com.cab302.teachscope.models.dao;

import com.cab302.teachscope.models.entities.Student;
import com.cab302.teachscope.models.entities.WeeklyForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockFormDao implements FormDao {

    private final Map<String, Student> forms = new HashMap<>();

    @Override
    public WeeklyForm create(WeeklyForm form) {
        return null;
    }

    @Override
    public WeeklyForm findById(String id) {
        return null;
    }

    @Override
    public List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) {
        return List.of();
    }

    @Override
    public List<WeeklyForm> findAll() {
        return List.of();
    }

    @Override
    public void update(WeeklyForm form) {

    }

    @Override
    public void delete(String id) {

    }
}
