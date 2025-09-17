package com.cab302.teachscope.models.entities;


import java.util.Date;
import java.util.UUID;

public class WeeklyForm {
    private String id;
    private String studentId;
    private int term;
    private int week;
    private int attendanceDays;
    private boolean wasLate;
    private boolean homeworkDone;
    private int attentionScore;
    private int literacyScore;
    private int numeracyScore;
    private int understandingScore;
    private int behaviourScore;
    private String createdAt;

    public WeeklyForm(String id, String studentId, int term, int week, int attendanceDays,
                      boolean wasLate, boolean homeworkDone, int attentionScore,
                      int literacyScore, int numeracyScore, int understandingScore,
                      int behaviourScore, String createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.term = term;
        this.week = week;
        this.attendanceDays = attendanceDays;
        this.wasLate = wasLate;
        this.homeworkDone = homeworkDone;
        this.attentionScore = attentionScore;
        this.literacyScore = literacyScore;
        this.numeracyScore = numeracyScore;
        this.understandingScore = understandingScore;
        this.behaviourScore = behaviourScore;
        this.createdAt = createdAt;
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

    public boolean isWasLate() { return wasLate; }
    public void setWasLate(boolean wasLate) { this.wasLate = wasLate; }

    public boolean isHomeworkDone() { return homeworkDone; }
    public void setHomeworkDone(boolean homeworkDone) { this.homeworkDone = homeworkDone; }

    public int getAttentionScore() { return attentionScore; }
    public void setAttentionScore(int attentionScore) { this.attentionScore = attentionScore; }

    public int getLiteracyScore() { return literacyScore; }
    public void setLiteracyScore(int literacyScore) { this.literacyScore = literacyScore; }

    public int getNumeracyScore() { return numeracyScore; }
    public void setNumeracyScore(int numeracyScore) { this.numeracyScore = numeracyScore; }

    public int getUnderstandingScore() { return understandingScore; }
    public void setUnderstandingScore(int understandingScore) { this.understandingScore = understandingScore; }

    public int getBehaviourScore() { return behaviourScore; }
    public void setBehaviourScore(int behaviourScore) { this.behaviourScore = behaviourScore; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
