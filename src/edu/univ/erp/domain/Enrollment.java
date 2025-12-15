package edu.univ.erp.domain;

import edu.univ.erp.data.ERPDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int sectionId;
    private String status;

    private String courseTitle;

    private String rollNo;
    private String name;
    private double assignment;
    private double midSem;
    private double endSem;
    private double quiz;

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseTitle() {
        return courseTitle;
    }


    public void setRollNo(String rollNo) {
        this.rollNo=rollNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public double getAssignment() {
        return assignment;
    }

    public double getMidSem() {
        return midSem;
    }

    public double getEndSem() {
        return endSem;
    }

    public double getQuiz() {
        return quiz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignment(double assignment) {
        this.assignment = assignment;
    }

    public void setMidSem(double midSem) {
        this.midSem = midSem;
    }

    public void setEndSem(double endSem) {
        this.endSem = endSem;
    }

    public void setQuiz(double quiz) {
        this.quiz = quiz;
    }
}
