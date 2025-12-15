package edu.univ.erp.data;

import edu.univ.erp.domain.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstructorAllotmentTable {
    public List<Section> getAvailableSections(String semester, String courseCode){
        String query="SELECT s.section_id,s.course_id,s.instructor_id,s.day_time,s.room,s.capacity,s.semester,s.year FROM sections s JOIN courses c ON s.course_id=c.course_id WHERE (s.instructor_id IS NULL OR s.instructor_id=0) AND s.semester=? AND c.code=?";
        List<Section> list=new ArrayList<>();
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,semester);
            stmt.setString(2,courseCode);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Section s=new Section();
                s.setSectionId(rs.getInt("section_id"));
                s.setCourseId(rs.getInt("course_id"));
                s.setInstructorId(rs.getInt("instructor_id"));
                s.setDayTime(rs.getString("day_time"));
                s.setRoom(rs.getString("room"));
                s.setCapacity(rs.getInt("capacity"));
                s.setYear(rs.getInt("year"));
                s.setSemester(rs.getString("semester"));
                list.add(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Section> getSectionsByInstructor(int instructorId){
        List<Section> list = new ArrayList<>();
        String query="SELECT section_id, course_id, instructor_id, day_time,room, capacity, semester, year FROM sections WHERE instructor_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,instructorId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Section s=new Section();
                s.setSectionId(rs.getInt("section_id"));
                s.setCourseId(rs.getInt("course_id"));
                s.setInstructorId(rs.getInt("instructor_id"));
                s.setDayTime(rs.getString("day_time"));
                s.setRoom(rs.getString("room"));
                s.setCapacity(rs.getInt("capacity"));
                s.setSemester(rs.getString("semester"));
                s.setYear(rs.getInt("year"));
                list.add(s);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean assignInstructorToSection(int instructorId, int sectionId){
        String query="UPDATE sections SET instructor_id=? WHERE section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,instructorId);
            stmt.setInt(2,sectionId);
            int updated= stmt.executeUpdate();
            return updated==1;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getAvailableInstructors(int courseId) {
        List<Integer> list=new ArrayList<>();
        String query="SELECT i.user_id, i.designation, i.department FROM instructors i WHERE i.user_id NOT IN(SELECT DISTINCT instructor_id FROM sections WHERE instructor_id IS NOT NULL)";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("user_id"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
