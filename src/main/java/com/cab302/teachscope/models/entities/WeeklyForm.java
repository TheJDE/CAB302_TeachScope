package com.cab302.teachscope.models.entities;


import com.cab302.teachscope.util.IdUtil;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class WeeklyForm {
    /**
     * Form ID
     */
    private String id;

    /**
     * Student ID
     */
    private String studentId;

    /**
     * Term of form
     */
    private int term;

    /**
     * Week of form
     */
    private int week;

    /**
     * Number of days attended in the week
     */
    private int attendanceDays;

    /**
     * Number of days late in the week
     */
    private int daysLate;

    /**
     * Attention score for the week (0-2)
     */
    private int attentionScore;

    /**
     * Was homework completed this week?
     */
    private boolean homeworkDone;

    /**
     * Participation score for the week (0-2)
     */
    private int participationScore;

    /**
     * Literacy score for the week (0-2)
     */
    private int literacyScore;

    /**
     * Numeracy score for the week (0-2)
     */
    private int numeracyScore;

    /**
     * Understanding score for the week (0-2)
     */
    private int understandingScore;

    /**
     * Behaviour score for the week (0-2)
     */
    private int behaviourScore;

    /**
     * Peer interaction score for the week (0-2)
     */
    private int peerInteractionScore;

    /**
     * Respect for rules score for the week (0-2)
     */
    private int respectForRulesScore;

    /**
     * Most common emotional state for the week
     */
    private String emotionalState;

    /**
     * Additional notes on the student for the week
     */
    private String teacherConcerns;

    /**
     * Contructor
     * @param id
     * @param studentId
     * @param term
     * @param week
     * @param attendanceDays
     * @param daysLate
     * @param attentionScore
     * @param homeworkDone
     * @param participationScore
     * @param literacyScore
     * @param numeracyScore
     * @param understandingScore
     * @param behaviourScore
     * @param peerInteractionScore
     * @param respectForRulesScore
     * @param emotionalState
     * @param teacherConcerns
     */
    public WeeklyForm(Optional<String> id,
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
        if (id.isPresent()) {
            setId(id.get());
        } else {
            setId(IdUtil.generateIdString());
        }
        setStudentId(studentId);
        setTerm(term);
        setWeek(week);
        setAttendanceDays(attendanceDays);
        setDaysLate(daysLate);
        setAttentionScore(attentionScore);
        setHomeworkDone(homeworkDone);
        setParticipationScore(participationScore);
        setLiteracyScore(literacyScore);
        setNumeracyScore(numeracyScore);
        setUnderstandingScore(understandingScore);
        setBehaviourScore(behaviourScore);
        setPeerInteractionScore(peerInteractionScore);
        setRespectForRulesScore(respectForRulesScore);
        setEmotionalState(emotionalState);
        setTeacherConcerns(teacherConcerns);
    }

    // Empty constructor for frameworks
    public WeeklyForm() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getTerm() { return term; }
    public void setTerm(int term) { this.term = term; }

    public int getWeek() { return week; }
    public void setWeek(int week) { this.week = week; }

    public int getAttendanceDays() { return attendanceDays; }
    public void setAttendanceDays(int attendanceDays) { this.attendanceDays = attendanceDays; }

    public int getDaysLate() { return daysLate; }
    public void setDaysLate(int daysLate) { this.daysLate = daysLate; }

    public int getAttentionScore() { return attentionScore; }
    public void setAttentionScore(int attentionScore) { this.attentionScore = attentionScore; }

    public boolean isHomeworkDone() { return homeworkDone; }
    public void setHomeworkDone(boolean homeworkDone) { this.homeworkDone = homeworkDone; }

    public int getParticipationScore() { return participationScore; }
    public void setParticipationScore(int participationScore) { this.participationScore = participationScore; }

    public int getLiteracyScore() { return literacyScore; }
    public void setLiteracyScore(int literacyScore) { this.literacyScore = literacyScore; }

    public int getNumeracyScore() { return numeracyScore; }
    public void setNumeracyScore(int numeracyScore) { this.numeracyScore = numeracyScore; }

    public int getUnderstandingScore() { return understandingScore; }
    public void setUnderstandingScore(int understandingScore) { this.understandingScore = understandingScore; }

    public int getBehaviourScore() { return behaviourScore; }
    public void setBehaviourScore(int behaviourScore) { this.behaviourScore = behaviourScore; }

    public int getPeerInteractionScore() { return peerInteractionScore; }
    public void setPeerInteractionScore(int peerInteractionScore) { this.peerInteractionScore = peerInteractionScore; }

    public int getRespectForRulesScore() { return respectForRulesScore; }
    public void setRespectForRulesScore(int respectForRulesScore) { this.respectForRulesScore = respectForRulesScore; }

    public String getEmotionalState() { return emotionalState; }
    public void setEmotionalState(String emotionalState) { this.emotionalState = emotionalState; }

    public String getTeacherConcerns() { return teacherConcerns; }
    public void setTeacherConcerns(String teacherConcerns) { this.teacherConcerns = teacherConcerns; }
}
