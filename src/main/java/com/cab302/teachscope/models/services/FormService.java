package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.WeeklyForm;
import com.cab302.teachscope.models.entities.Student;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;

public class FormService {

    private final FormDao formDao;

    /**
     * Constructor
     * @param formDao Form data access object.
     */
    public FormService(FormDao formDao) { this.formDao = formDao;}

    public void createForm(String id,
                                 String studentId,
                                 int term,
                                 int week,
                                 int attendanceDays,
                                 int daysLate,
                                 int attentionScore,
                                 boolean homeworkDone,
                                 int participationScore,
                                 int literacyScore,
                                 int numeracyScore,
                                 int understandingScore,
                                 int behaviourScore,
                                 int peerInteractionScore,
                                 int respectForRulesScore,
                                 String emotionalState,
                                 String teacherConcerns
    ) {
        WeeklyForm form = new WeeklyForm(
                id,
                studentId,
                term,
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

        validateForm(form);

        try {
            formDao.create(form);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public WeeklyForm getForm(String id) {
        if (id == null || !id.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"))
        {
          throw new IllegalArgumentException("Invalid id");
        }

        try {
            return formDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WeeklyForm> getFormsForStudent(String studentId, int term, int fromWeek, int toWeek) {
        if (studentId == null || !studentId.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"))
        {
            throw new IllegalArgumentException("Invalid id");
        }

        if (term < 0 || term > 5) {
            throw new IllegalArgumentException("Invalid term");
        }

        if (fromWeek < 0 || fromWeek > 12 || fromWeek > toWeek) {
            throw new IllegalArgumentException("Invalid term");
        }

        try {
            return formDao.findByStudentAndRange(studentId, term, fromWeek, toWeek);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WeeklyForm> getAllForms() {
        try {
            return formDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateForm(WeeklyForm form) {
        validateForm(form);

        try {
            formDao.update(form);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteForm(String id) {
        try {
            formDao.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateForm(WeeklyForm form) {
        // ID
        if (form.getId() == null || !form.getId().matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"))
        {
            throw new IllegalArgumentException("Invalid form ID");
        }

        // Student ID
        if (form.getStudentId() == null || !form.getStudentId().matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"))
        {
            throw new IllegalArgumentException("Invalid student ID");
        }

        // Term
        if (form.getTerm() < 1 || form.getTerm() > 4) {
            throw new IllegalArgumentException("Term must be between 1-4");
        }

        // Week
        if (form.getWeek() < 1 || form.getWeek() > 12) {
            throw new IllegalArgumentException("Week must be between 1-12");
        }

        // Attendance Days
        if (form.getWeek() < 0 || form.getWeek() > 5) {
            throw new IllegalArgumentException("Attendance days must be between 0-5");
        }

        // Days Late
        if (form.getDaysLate() < 0 || form.getDaysLate() > 5) {
            throw new IllegalArgumentException("Days late must be between 0-5");
        }

        // Scores
        if (
            form.getParticipationScore() < 0 || form.getParticipationScore() > 2 ||
            form.getLiteracyScore() < 0 || form.getLiteracyScore() > 2 ||
            form.getNumeracyScore() < 0 || form.getNumeracyScore() > 2 ||
            form.getUnderstandingScore() < 0 || form.getUnderstandingScore() > 2 ||
            form.getBehaviourScore() < 0 || form.getBehaviourScore() > 2 ||
            form.getPeerInteractionScore() < 0 || form.getPeerInteractionScore() > 2 ||
            form.getRespectForRulesScore() < 0 || form.getRespectForRulesScore() > 2
        ) {
            throw new IllegalArgumentException("All scores must be between 0-2");
        }

        // Emotional State
        if (
            form.getEmotionalState() == null ||
            form.getEmotionalState() != "Happy" ||
            form.getEmotionalState() != "Neutral" ||
            form.getEmotionalState() != "Withdrawn" ||
            form.getEmotionalState() != "Anxious"
        ) {
            throw new IllegalArgumentException("Invalid emotional state");
        }

        // Teacher Concerns
        if (form.getTeacherConcerns() == null) {
            throw new IllegalArgumentException("Invalid teacher concerns");
        }
    }
}
