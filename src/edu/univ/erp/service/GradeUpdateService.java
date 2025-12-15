package edu.univ.erp.service;

import edu.univ.erp.data.GradeTable;
import edu.univ.erp.domain.Grade;

import java.math.BigDecimal;

public class GradeUpdateService {
    private GradeTable gradeTable=new GradeTable();
    private MaintenanceService m=new MaintenanceService();

    private String checkMaintenance(){
        if(m.isMaintenanceEnabled()){
            return m.getMaintenanceMessage();
        }
        return null;
    }

    public String computeAndSave(Grade g, double assignmentWt, double midSemWt, double endSemWt, double quizWt){
        String maintenanceMsg=checkMaintenance();
        if(maintenanceMsg!=null){
            return maintenanceMsg;
        }

        double total=g.getAssignment()*assignmentWt + g.getMidSem()*midSemWt + g.getEndSem()*endSemWt + g.getQuiz()*quizWt;
        g.setScore(total);
        String gradeLetter;
        if(total>=90){
            gradeLetter="A+";
        }else if(total>=80){
            gradeLetter="A";
        } else if (total>=70) {
            gradeLetter="B";
        }
        else if(total>=60) {
            gradeLetter="C";
        }
        else{
            gradeLetter="F";
        }
        g.setFinalGrade(gradeLetter);
        gradeTable.updateGrade(g);
        return "Grades updated successfully";
    }
}
