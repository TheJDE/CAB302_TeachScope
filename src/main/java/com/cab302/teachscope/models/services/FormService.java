package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.WeeklyForm;
import com.cab302.teachscope.models.entities.Student;

import java.sql.SQLException;
import java.util.List;

public class FormService {

    private final FormDao formDao;
    private final StudentDao studentDao;

    public FormService(FormDao formDao, StudentDao studentDao) {
        this.formDao = formDao;
        this.studentDao = studentDao;
    }

    public WeeklyForm createForm(WeeklyForm form) throws SQLException {
        Student student = studentDao.getStudent(form.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("No student found with ID: " + form.getStudentId());
        }
        validateForm(form);
        return formDao.create(form);
    }

    public WeeklyForm getForm(String id) {
        return formDao.findById(id);
    }

    public List<WeeklyForm> getFormsForStudent(String studentId, int term, int fromWeek, int toWeek) {
        return formDao.findByStudentAndRange(studentId, term, fromWeek, toWeek);
    }

    public List<WeeklyForm> getAllForms() {
        return formDao.findAll();
    }

    public void updateForm(WeeklyForm form) {
        validateForm(form);
        formDao.update(form);
    }

    public void deleteForm(String id) {
        formDao.delete(id);
    }

    private void validateForm(WeeklyForm form) {
        if (form.getTerm() <= 0 || form.getWeek() <= 0) {
            throw new IllegalArgumentException("Term and Week must be positive.");
        }
        if (form.getAttendanceDays() < 0) {
            throw new IllegalArgumentException("Attendance days cannot be negative.");
        }
    }
}
