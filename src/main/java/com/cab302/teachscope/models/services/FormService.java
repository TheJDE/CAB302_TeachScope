package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.WeeklyForm;
import com.cab302.teachscope.models.entities.Student;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.*;

/**
 * Service class for weekly forms.
 */
public class FormService {

    /**
     * The DAO used by the service.
     */
    private final FormDao formDao;

    /**
     * Constructor
     * @param formDao Form data access object.
     */
    public FormService(FormDao formDao) { this.formDao = formDao;}

    /**
     * Create a new weekly form.
     * @param studentId Student Id.
     * @param term Term for the form.
     * @param week Week for the form.
     * @param attendanceDays Number of days attended.
     * @param daysLate Number of days late.
     * @param attentionScore 0-2 score for attention.
     * @param homeworkDone Has student done the homework.
     * @param participationScore 0-2 score for participation.
     * @param literacyScore 0-2 score for literacy.
     * @param numeracyScore 0-2 score for numeracy.
     * @param understandingScore 0-2 score for understanding.
     * @param behaviourScore 0-2 score for behaviour.
     * @param peerInteractionScore 0-2 score for peer interaction.
     * @param respectForRulesScore 0-2 score for respect for rules.
     * @param emotionalState Student's average emotional state.
     * @param teacherConcerns Additional notes on the student.
     */
    public void createForm(
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
                Optional.empty(),
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
            throw new RuntimeException("Duplicate form created for student. Please change the term/week of the report to one that hasn't been created.");
        }
    }

    /**
     * Get a form by Id.
     * @param id Form Id.
     * @return Matching form.
     */
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

    /**
     * Gets a list of all existing forms.
     * @return List of all existing forms.
     */
    public List<WeeklyForm> getAllForms() {
        try {
            return formDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a list of forms matching student Id and a range of term/weeks.
     * @param studentId Student's Id
     * @param term Term to match.
     * @param fromWeek Beginning of week range. Inclusive.
     * @param toWeek End of week range. Inclusive.
     * @return List of forms.
     */
    public List<WeeklyForm> getFormsForStudentAndRange(String studentId, int term, int fromWeek, int toWeek) {
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

    /**
     * Gets all forms for a given student.
     * @param studentId Student's Id.
     * @return List of forms for a student.
     */
    public List<WeeklyForm> getAllFormsForStudent(String studentId) {
        if (studentId == null || !studentId.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$"))
        {
            throw new IllegalArgumentException("Invalid id");
        }

        try {
            return formDao.findAllForStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all forms for a given week and term
     * @param term The term to query
     * @param week The week to query
     * @return A list of maps containing a form ID and a student ID
     */
    public List<Map<String, String>> getAllFormsForGivenWeek(int term, int week) {
        // Term
        if (term < 1 || term > 4) {
            throw new IllegalArgumentException("Term must be between 1-4");
        }

        // Week
        if (week < 1 || week > 10) {
            throw new IllegalArgumentException("Week must be between 1-10");
        }

        try {
            return formDao.findAllForGivenWeek(term, week);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates form values.
     * @param form Values to replace existing form with.
     */
    public void updateForm(WeeklyForm form) {
        validateForm(form);

        try {
            formDao.update(form);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a specific form.
     * @param id Id of form to delete.
     */
    public void deleteForm(String id) {
        try {
            formDao.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates form data.
     * @param form The form to validate.
     */
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
        if (form.getWeek() < 1 || form.getWeek() > 10) {
            throw new IllegalArgumentException("Week must be between 1-10");
        }

        // Attendance Days
        if (form.getAttendanceDays() < 0 || form.getAttendanceDays() > 5) {
            throw new IllegalArgumentException("Attendance days must be between 0-5");
        }

        // Days Late
        if (form.getDaysLate() < 0 || form.getDaysLate() > 5) {
            throw new IllegalArgumentException("Days late must be between 0-5");
        }

        // Scores
        if (
            form.getParticipationScore() < 0 || form.getParticipationScore() > 2 ||
            form.getAttentionScore() < 0 || form.getAttentionScore() > 2 ||
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
        List<String> acceptedStates = Arrays.asList("Happy", "Neutral", "Withdrawn", "Anxious");
        if (
            form.getEmotionalState() == null ||
            !acceptedStates.contains(form.getEmotionalState())
        ) {
            throw new IllegalArgumentException("Invalid emotional state");
        }

        // Teacher Concerns
        if (form.getTeacherConcerns() == null) {
            throw new IllegalArgumentException("Invalid teacher concerns");
        }
    }
}
