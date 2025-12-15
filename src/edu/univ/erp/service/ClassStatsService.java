package edu.univ.erp.service;

import edu.univ.erp.data.GradeTable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClassStatsService {
    private GradeTable gradeTable=new GradeTable();
    public double getAverageScore(int sectionId){
        List<Double> scores=gradeTable.getScoresForSection(sectionId);
        if(scores.isEmpty()){
            return 0;
        }
        double sum=0;
        for(double s:scores){
            sum+=s;
        }
        return sum/scores.size();
    }

    public double getMaxScore(int sectionId){
        List<Double> scores=gradeTable.getScoresForSection(sectionId);
        return scores.isEmpty() ? 0 : Collections.max(scores);
    }

    public double getMinScore(int sectionId){
        List<Double> scores=gradeTable.getScoresForSection(sectionId);
        return scores.isEmpty() ? 0 : Collections.min(scores);
    }

    public Map<String, Integer> getGradeDistribution(int sectionId){
        return gradeTable.getGradeDistribution(sectionId);
    }

    public int getTotalStudents(int sectionId){
        return gradeTable.countStudentsInSection(sectionId);
    }

}
