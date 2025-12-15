package edu.univ.erp.service;

import edu.univ.erp.data.InstructorAllotmentTable;
import edu.univ.erp.data.SettingsTable;
import edu.univ.erp.domain.Section;

import java.util.List;

public class InstructorAllotmentService {
    private final InstructorAllotmentTable table=new InstructorAllotmentTable();
    private final SettingsTable settings=new SettingsTable();

    public List<Section> getAvailableSections(String semester, String courseCode){
        return table.getAvailableSections(semester,courseCode);
    }
    public List<Section> getAssignedSections(int instructorId ){
        return table.getSectionsByInstructor(instructorId);
    }
    public String assignInstructor(int instructorId, int sectionId){
        if (settings.isMaintenanceMode()){
            return "System in maintenance";
        }
        boolean ok=table.assignInstructorToSection(instructorId, sectionId);
        return ok ? "Instructor assigned successfully." : "Failed to assign an instructor";
    }

    public List<Integer> getAvailableInstructors(int courseId) {
        return table.getAvailableInstructors(courseId);
    }
}
