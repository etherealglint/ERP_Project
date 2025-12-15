package edu.univ.erp.service;

import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.data.SectionTable;
import edu.univ.erp.data.SettingsTable;
import edu.univ.erp.domain.Section;

import java.util.List;

public class CourseRegestrationService {
    private EnrollmentTable enrollmentTable=new EnrollmentTable();
    private static SectionTable sectionTable=new SectionTable();
    private SettingsTable settingsTable=new SettingsTable();

    public static List<Section> getCatalog(){
        return sectionTable.getCatalog();
    }

    private String checkMaintenance(){
        MaintenanceService m=new MaintenanceService();
        if (m.isMaintenanceEnabled()){
            return m.getMaintenanceMessage();
        }
        return null;
    }

    public String register(int studentId, int sectionId){
        String maintenanceMsg=checkMaintenance();
        if(maintenanceMsg!=null){
            return maintenanceMsg;
        }
//        if(settingsTable.isMaintenanceMode()){
//            return "System is in maintenance mode.";
//        }
        if(enrollmentTable.isAlreadyEnrolled(studentId,sectionId)){
            return "You are already enrolled in this section";
        }
        int enrolledCount=enrollmentTable.countEnrolledStudents(sectionId);
        int capacity=sectionTable.getCapacity(sectionId);
        if(enrolledCount>=capacity){
            return "section is full.";
        }
        enrollmentTable.register(studentId, sectionId);
        return "Regestration sucessful.";
    }

    public String drop(int studentId, int sectionId){
        String maintenanceMsg=checkMaintenance();
        if(maintenanceMsg!=null){
            return maintenanceMsg;
        }
        if(enrollmentTable.checkDropDeadline(studentId)){
            enrollmentTable.drop(studentId, sectionId);
            return "Course dropped";
        }
        return "Deadline Passed !";
    }

    public List<Section> getSavedCourses(int studentId) {
        EnrollmentTable table=new EnrollmentTable();
        return table.getStudentEnrolledCourses(studentId);
    }
}
