package com.cab302.teachscope.models.dao;


import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FormDao {
    void create(WeeklyForm form) throws SQLException;

    WeeklyForm findById(String id) throws SQLException;

    List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    Map<String, Double> findAverageScoresForStudent(String studentId) throws SQLException;

    Map<String, Double> findGlobalAverageScores() throws SQLException;

    List<WeeklyForm> findAll() throws SQLException;

    List<WeeklyForm> findAllForStudent(String studentId) throws SQLException;

    List<Map<String, String>> findAllForGivenWeek(int term, int week) throws SQLException;

    void update(WeeklyForm form) throws SQLException;

    void delete(String id) throws SQLException;
}
