package edu.univ.erp.domain;

public class Grade {
    private int enrollmentId;
//    private String component;
    private double score;
    private double assignment;
    private double endSem;
    private double midSem;
    private double quiz;
    private String finalGrade;

    private String rollNo;
    private String Name;

    public Grade(int enrollmentId, double score, double assignment, double endSem, double midSem, double quiz, String finalGrade) {
        this.enrollmentId = enrollmentId;
        this.score = score;
        this.assignment = assignment;
        this.endSem = endSem;
        this.midSem = midSem;
        this.quiz = quiz;
        this.finalGrade = finalGrade;
    }

    public Grade() {

    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getAssignment() {
        return assignment;
    }

    public void setAssignment(double assignment) {
        this.assignment = assignment;
    }

    public double getEndSem() {
        return endSem;
    }

    public void setEndSem(double endSem) {
        this.endSem = endSem;
    }

    public double getMidSem() {
        return midSem;
    }

    public void setMidSem(double midSem) {
        this.midSem = midSem;
    }

    public double getQuiz() {
        return quiz;
    }

    public void setQuiz(double quiz) {
        this.quiz = quiz;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
