package edu.univ.erp.service;

import edu.univ.erp.data.ERPDatabaseConnection;
import edu.univ.erp.data.GradeTable;
import edu.univ.erp.domain.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class GradeService {
    private GradeTable gradeTable=new GradeTable();

    public List<Grade> loadGradesForSection(int sectionId){
        return gradeTable.loadGradesForSection(sectionId);
    }
    public List<Grade> loadGrades(int enrollmentId){
        return gradeTable.getGradesForEnrollment(enrollmentId);
    }
}
