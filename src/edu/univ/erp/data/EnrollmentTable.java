package edu.univ.erp.data;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Section;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentTable {

    public boolean isAlreadyEnrolled(int studentId, int sectionId){
        String query = "SELECT COUNT(*) FROM enrollments WHERE student_id=? AND section_id=? AND status='enrolled' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            stmt.setInt(2,sectionId);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            return rs.getInt(1)>0;
        }
        catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    public boolean isAlreadyDropped(int studentId, int sectionId){
        String query = "SELECT COUNT(*) FROM enrollments WHERE student_id=? AND section_id=? AND status='dropped' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            stmt.setInt(2,sectionId);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            return rs.getInt(1)>0;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int countEnrolledStudents(int sectionId){
        String query="SELECT COUNT(*) FROM enrollments WHERE section_id=? AND status='enrolled' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (Exception e){
            e.printStackTrace();
            return 999999; //smaple
        }
    }

    public void register(int studentId, int sectionId){

        if (isAlreadyEnrolled(studentId, sectionId)){
            return;
        }
        if(isAlreadyDropped(studentId,sectionId)){
            String query="UPDATE enrollments SET status='enrolled' WHERE student_id=? AND section_id=?";
            try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
                stmt.setInt(1,studentId);
                stmt.setInt(2,sectionId);
                stmt.executeUpdate();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return;
        }

        String query="INSERT INTO enrollments(student_id,section_id,status) VALUES (?,?,'enrolled')";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            stmt.setInt(2,sectionId);
            stmt.executeUpdate();
//            return "Regestration success"
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void drop(int studentId, int sectionId){
        String query="UPDATE enrollments SET status='dropped' WHERE student_id=? AND section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            stmt.setInt(2,sectionId);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Section> getStudentEnrolledCourses(int studentId) {
        List<Section> list=new ArrayList<>();
        String query="SELECT s.section_id,s.year, s.semester, s.room, s.day_time, s.course_id, c.title, c.credits, s.capacity, s.instructor_id FROM enrollments e JOIN sections s ON e.section_id=s.section_id JOIN courses c ON s.course_id=c.course_id WHERE e.student_id=? AND e.status='enrolled' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Section s=new Section();
                s.setSectionId(rs.getInt("section_id"));
                s.setCourseId(rs.getInt("course_id"));
                s.setCourseTitle(rs.getString("title"));
                s.setCredits(rs.getInt("credits"));
                s.setCapacity(rs.getInt("capacity"));
                s.setInstructorId(rs.getInt("instructor_id"));
                s.setYear(rs.getInt("year"));
                s.setSemester(rs.getString("semester"));
                s.setRoom(rs.getString("room"));
                s.setDayTime(rs.getString("day_time"));
                list.add(s);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Enrollment> getEnrollmentsForSection(int sectionId){
        List<Enrollment> list= new ArrayList<>();
        String query="SELECT e.enrollment_id, e.student_id, s.roll_no, s.name, g.assignment, g.mid_sem, g.end_sem, g.quiz FROM enrollments e JOIN students s ON e.enrollment_id=s.user_id LEFT JOIN grades g ON e.enrollment_id=g.enrollment_id WHERE e.section_id=? AND e.status='enrolled' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Enrollment e=new Enrollment();
                e.setEnrollmentId(rs.getInt("enrollment_id"));
                e.setStudentId(rs.getInt("student_id"));
                e.setRollNo(rs.getString("roll_no"));
                e.setName(rs.getString("name"));
                e.setAssignment(rs.getDouble("assignment"));
                e.setEndSem(rs.getDouble("end_sem"));
                e.setMidSem(rs.getDouble("mid_sem"));
                e.setQuiz(rs.getDouble("quiz"));
                list.add(e);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkDropDeadline(int enrollmentId){
        String query="SELECT drop_deadline FROM enrollments WHERE enrollment_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,enrollmentId);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                Date deadline=rs.getDate("drop_deadline");
                Date today=new Date(System.currentTimeMillis());
                return today.compareTo(deadline) <=0 ;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Enrollment> getEnrollmentForStudent(int studentId){
        String query="SELECT e.enrollment_id, e.student_id, e.section_id, s.course_id,c.title AS course_title FROM enrollments e JOIN sections s ON e.section_id=s.section_id JOIN courses c ON s.course_id=c.course_id WHERE e.student_id=? AND e.status='enrolled' ";
        List<Enrollment> list =new ArrayList<>();

        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,studentId);
            ResultSet rs=stmt.executeQuery();

            while(rs.next()){
                Enrollment e=new Enrollment();
                e.setEnrollmentId(rs.getInt("enrollment_id"));
                e.setStudentId(rs.getInt("student_id"));
                e.setSectionId(rs.getInt("section_id"));
                e.setCourseTitle(rs.getString("course_title"));
                list.add(e);
            }
        }
        catch (Exception er){
            er.printStackTrace();
        }
        return list;
    }

}
