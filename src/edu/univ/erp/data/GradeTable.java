package edu.univ.erp.data;

import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeTable {
    public List<Grade> getGradesForEnrollment(int enrollmentId){
        List<Grade> list=new ArrayList<>();
        String query="SELECT enrollment_id, assignment,mid_sem,end_sem,quiz, score,final_grade FROM grades WHERE enrollment_id=?";
        try( Connection con=ERPDatabaseConnection.getConnection();
                PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,enrollmentId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Grade g=new Grade(
                        rs.getInt("enrollment_id"),
//                        rs.getString("component"),
                        rs.getDouble("assignment"),
                        rs.getDouble("mid_sem"),
                        rs.getDouble("end_sem"),
                        rs.getDouble("quiz"),
                        rs.getDouble("score"),
                        rs.getString("final_grade")
                );
                list.add(g);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateGrade(Grade g){
        String query="UPDATE grades SET assignment=?, mid_sem=?, end_sem=?, quiz=?, score=?, final_grade=? WHERE enrollment_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setDouble(1,g.getAssignment());
            stmt.setDouble(2,g.getMidSem());
            stmt.setDouble(3,g.getEndSem());
            stmt.setDouble(4,g.getQuiz());
            stmt.setDouble(5,g.getScore());
            stmt.setString(6,g.getFinalGrade());
            stmt.setInt(7,g.getEnrollmentId());

            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Double> getScoresForSection(int sectionId){
        List<Double> scores = new ArrayList<>();
        String query="SELECT g.score FROM grades g JOIN enrollments e ON g.enrollment_id=e.enrollment_id WHERE e.section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
                scores.add(rs.getDouble("score"));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return scores;
    }

    public Map<String,Integer> getGradeDistribution(int sectionId){
        Map<String,Integer> m=new HashMap<>();
        String query="SELECT g.final_grade, COUNT(*) as total FROM grades g JOIN enrollments e ON e.enrollment_id=g.enrollment_id WHERE e.section_id= ? GROUP BY g.final_grade";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                m.put(rs.getString("final_grade"),rs.getInt("total"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return m;
    }

    public int countStudentsInSection(int sectionId){
        String query="SELECT COUNT(*) AS total FROM enrollments WHERE section_id=? AND status='enrolled' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Grade> loadGradesForSection(int sectionId){
        List<Grade> list=new ArrayList<>();
        String query="SELECT g.enrollment_id, g.assignment, s.name, g.mid_Sem, g.end_sem, g.quiz, s.user_id AS student_id, s.roll_no, s.program FROM grades g JOIN enrollments e ON g.enrollment_id=e.enrollment_id JOIN students s ON e.student_id=s.user_id WHERE e.section_id=? AND e.status='enrolled' ";
        try(Connection con= ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Grade e=new Grade();
                e.setEnrollmentId(rs.getInt("enrollment_id"));
//                e.setStudentId(rs.getInt("student_id"));
                e.setRollNo(rs.getString("roll_no"));
                e.setName(rs.getString("name"));
                e.setAssignment(rs.getDouble("assignment"));
                e.setMidSem(rs.getDouble("mid_Sem"));
                e.setEndSem(rs.getDouble("end_sem"));
                e.setQuiz(rs.getDouble("quiz"));
                list.add(e);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
