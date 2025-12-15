package edu.univ.erp.service;

import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.domain.Enrollment;

import java.util.List;

public class EnrollmentService {
    private final EnrollmentTable enrollmentTable=new EnrollmentTable();
    public List<Enrollment> getEnrollmentsForStudent(int studentId){
        return enrollmentTable.getEnrollmentForStudent(studentId);
    }
}
