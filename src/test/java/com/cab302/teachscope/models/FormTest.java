package com.cab302.teachscope.models;


import com.cab302.teachscope.models.dao.MockFormDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.MockUserDao;
import com.cab302.teachscope.models.entities.WeeklyForm;
import com.cab302.teachscope.models.services.FormService;
import com.cab302.teachscope.models.services.UserService;
import com.cab302.teachscope.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FormTest {
    private FormDao formdao;
    private FormService formService;

    private String id;
    private String StudentId;
    private int term;
    private int week;
    private int attenanceDays;
    private boolean wasLate;
    private boolean homeworkDone;
    private int attentionScore;
    private int literacyScore;
    private int numeracyScore;
    private int understandingScore;
    private int behaviourScore;
    private String createdAt;

    @BeforeEach
    public void setup() {
        formdao = new MockFormDao();
        formService = new FormService(formdao);

        id = "1";
        StudentId = "1";
        term = 1;
        week = 1;
        attenanceDays = 1;
        wasLate = false;
        homeworkDone = true;
        attentionScore = 1;
        literacyScore = 1;
        numeracyScore = 1;
        understandingScore = 1;
        behaviourScore = 1;
        createdAt = "Week 1, Term 1";

    }

    @Test
    void createFormValid() {

    }

    @Test
    void createFormInvalidTermTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    new WeeklyForm(
                            id,
                            StudentId,
                            0,
                            week,
                            attenanceDays,
                            wasLate,
                            homeworkDone,
                            attentionScore,
                            literacyScore,
                            numeracyScore,
                            understandingScore,
                            behaviourScore,
                            createdAt)
            );
        });
    }

    @Test
    void createFormInvalidTermTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    new WeeklyForm(
                            id,
                            StudentId,
                            5,
                            week,
                            attenanceDays,
                            wasLate,
                            homeworkDone,
                            attentionScore,
                            literacyScore,
                            numeracyScore,
                            understandingScore,
                            behaviourScore,
                            createdAt)
            );
        });
    }

    @Test
    void createFormInvalidWeekTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    new WeeklyForm(
                            id,
                            StudentId,
                            term,
                            0,
                            attenanceDays,
                            wasLate,
                            homeworkDone,
                            attentionScore,
                            literacyScore,
                            numeracyScore,
                            understandingScore,
                            behaviourScore,
                            createdAt)
            );
        });
    }

    @Test
    void createFormInvalidWeekTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    new WeeklyForm(
                            id,
                            StudentId,
                            term,
                            12,
                            attenanceDays,
                            wasLate,
                            homeworkDone,
                            attentionScore,
                            literacyScore,
                            numeracyScore,
                            understandingScore,
                            behaviourScore,
                            createdAt)
            );
        });
    }
}
