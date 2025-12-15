package edu.univ.erp.service;

import edu.univ.erp.data.SectionTable;
import edu.univ.erp.domain.Section;

import java.util.List;

public class InstructorSectionService {
    private SectionTable sectionTable=new SectionTable();
    public List<Section> getMySections(int instructorId){
        return sectionTable.getSectionsByInstructor(instructorId);
    }
}
