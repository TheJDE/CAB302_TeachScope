package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockFormDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.services.FormService;
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
    private int daysLate;
    private int attentionScore;
    private boolean homeworkDone;
    private int participationScore;
    private int literacyScore;
    private int numeracyScore;
    private int understandingScore;
    private int behaviourScore;
    private int peerInteractionScore;
    private int respectForRulesScore;
    private String createdAt;

    @BeforeEach
    public void setup() {
        formdao = new MockFormDao();
        formService = new FormService(formdao);

        id = "e215947f-4d73-4726-baaf-dbec6258968f";
        StudentId = "e215947f-4d73-4726-baaf-dbec6258968e";
        term = 1;
        week = 1;
        attenanceDays = 5;
        daysLate = 2;
        homeworkDone = true;
        participationScore = 2;
        attentionScore = 2;
        literacyScore = 0;
        numeracyScore = 0;
        understandingScore = 1;
        behaviourScore = 1;
        peerInteractionScore = 1;
        respectForRulesScore = 2;
        createdAt = "20/06/2025";
    }

    @Test
    void createFormValid() {

    }

    @Test
    void createFormInvalidTermTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                            id,
                            StudentId,
                            0,
                            week,
                            attenanceDays,
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
                            createdAt
            );
        });
    }

    @Test
    void createFormInvalidTermTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    id,
                    StudentId,
                    5,
                    week,
                    attenanceDays,
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
                    createdAt
            );
        });
    }

    @Test
    void createFormInvalidWeekTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    id,
                    StudentId,
                    term,
                    0,
                    attenanceDays,
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
                    createdAt
            );
        });
    }

    @Test
    void createFormInvalidWeekTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    id,
                    StudentId,
                    term,
                    13,
                    attenanceDays,
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
                    createdAt
            );
        });
    }
}
