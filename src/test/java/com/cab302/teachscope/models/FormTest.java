package com.cab302.teachscope.models;

import com.cab302.teachscope.models.dao.MockFormDao;
import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.MockStudentDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.services.FormService;
import com.cab302.teachscope.models.services.GenerateReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class FormTest {
    private FormDao formdao;
    private StudentDao studentDao;
    private FormService formService;
    private GenerateReportsService generateReportsService;

    private String id;
    private String StudentId;
    private int term;
    private int week;
    private int attendanceDays;
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
    private String emotionalState;
    private String teacherConcerns;

    @BeforeEach
    public void setup() {
        formdao = new MockFormDao();
        formService = new FormService(formdao);
        studentDao = new MockStudentDao();
        generateReportsService = new GenerateReportsService(formdao, studentDao);

        id = "e215947f-4d73-4726-baaf-dbec6258968f";
        StudentId = "e215947f-4d73-4726-baaf-dbec6258968e";
        term = 1;
        week = 1;
        attendanceDays = 5;
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
        emotionalState = "Happy";
        teacherConcerns = "No extra comments";
    }

    @Test
    void createFormValid() {
        assertDoesNotThrow(() -> formService.createForm(
                StudentId,
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
        ));
    }

    @Test
    void createFormInvalidTermTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    0,
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
        });
    }

    @Test
    void createFormInvalidTermTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    5,
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
        });
    }

    @Test
    void createFormInvalidWeekTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    0,
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
        });
    }

    @Test
    void createFormInvalidWeekTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    13,
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
        });
    }

    @Test
    void createFormInvalidAttendanceTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    -1,
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
        });
    }

    @Test
    void createFormInvalidAttendanceTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    6,
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
        });
    }

    @Test
    void createFormInvalidDaysLateTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    -1,
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
        });
    }

    @Test
    void createFormInvalidDaysLateTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    6,
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
        });
    }

    @Test
    void createFormInvalidAttentionScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    -1,
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
        });
    }

    @Test
    void createFormInvalidAttentionScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    3,
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
        });
    }

    @Test
    void createFormInvalidParticipationScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    -1,
                    literacyScore,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidParticipationScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    3,
                    literacyScore,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidLiteracyScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    -1,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidLiteracyScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    3,
                    numeracyScore,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidNumeracyScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    -1,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidNumeracyScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    3,
                    understandingScore,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidUnderstandingScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    numeracyScore,
                    -1,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidUnderstandingScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
                    term,
                    week,
                    attendanceDays,
                    daysLate,
                    attentionScore,
                    homeworkDone,
                    participationScore,
                    literacyScore,
                    numeracyScore,
                    3,
                    behaviourScore,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidBehaviourScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    -1,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidBehaviourScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    3,
                    peerInteractionScore,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidInteractionScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    -1,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidInteractionScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    3,
                    respectForRulesScore,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidRespectScoreTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    -1,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidRespectScoreTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    3,
                    emotionalState,
                    teacherConcerns
            );
        });
    }

    @Test
    void createFormInvalidEmotionalState() {
        assertThrows(IllegalArgumentException.class, () -> {
            formService.createForm(
                    StudentId,
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
                    "invalid",
                    teacherConcerns
            );
        });
    }

    @Test
    void getValidFormId() {
        formService.createForm(
                StudentId,
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

        String formID = formService.getAllForms().getFirst().getId();

        assertEquals(formID, formService.getForm(formID).getId());
    }

    @Test
    void getInvalidFormId() {
        assertNull(formService.getForm(id));
    }

    @Test
    void findGivenWeekValid() {
        formService.createForm(
                StudentId,
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

        String formID = formService.getAllForms().getFirst().getId();

        List<Map<String, String>> correctValue = List.of(
                Map.of("id", formID, "studentID", StudentId)
        );

        assertEquals(correctValue, formService.getAllFormsForGivenWeek(1, 1));
    }

    @Test
    void findGivenWeekEmpty() {
        assertTrue(formService.getAllFormsForGivenWeek(1, 1).isEmpty());
    }

    @Test
    void deleteValidFormId() {
        formService.createForm(
                StudentId,
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

        String formID = formService.getAllForms().getFirst().getId();

        try {
            formService.deleteForm(formID);

            assertNull(formService.getForm(formID));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteInvalidFormId() {
        formService.createForm(
                StudentId,
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

        String formID = formService.getAllForms().getFirst().getId();

        try {
            formService.deleteForm("invalid");
            assertEquals(formID, formService.getForm(formID).getId());
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    // STUDENT REPORTS TESTING
    @Test
    void studentAveragesValidID() {
        formService.createForm(
                StudentId,
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

        formService.createForm(
                StudentId,
                term,
                week + 1,
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

        assertDoesNotThrow(() -> generateReportsService.createReport(StudentId, term, 1, 10));
    }
}
