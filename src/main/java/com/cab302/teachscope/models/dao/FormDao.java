package com.cab302.teachscope.models.dao;


import com.cab302.teachscope.models.entities.WeeklyForm;

import java.util.List;

public interface FormDao {
    WeeklyForm create(WeeklyForm form);
    WeeklyForm findById(String id);
    List<WeeklyForm> findByStudentAndRange(String studentId, int term, int fromWeek, int toWeek);
    List<WeeklyForm> findAll();
    void update(WeeklyForm form);
    void delete(String id);
}
