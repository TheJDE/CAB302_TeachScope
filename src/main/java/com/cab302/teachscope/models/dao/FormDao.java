package com.cab302.teachscope.models.dao;


import com.cab302.teachscope.models.entities.WeeklyForm;

import java.sql.SQLException;
import java.util.List;

public interface FormDao {
    WeeklyForm create(WeeklyForm form) throws SQLException;

    WeeklyForm findById(String id) throws SQLException;

    List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek) throws SQLException;

    List<WeeklyForm> findAll() throws SQLException;

    void update(WeeklyForm form) throws SQLException;

    void delete(String id) throws SQLException;
}
