package edu.univ.erp.data;

import edu.univ.erp.domain.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionTable {
    public int getCapacity(int sectionId){
        String query="SELECT capacity FROM sections WHERE section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("capacity");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getCourseCode(int sectionId){
        String query="SELECT c.code FROM sections s JOIN courses c ON s.course_id=c.course_id WHERE s.section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            ResultSet rs=stmt.executeQuery();
            return rs.next() ? rs.getString("code"): null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Section> getCatalog(){
//        String query="SELECT s.section_id, c.code, c.title, i.name AS instructor, s.capacity, c.credits FROM sections s JOIN courses c ON s.course_id = c.course_id JOIN instructors i ON s.instructor_id=i.instructor_id";
        String query1="SELECT s.section_id,s.day_time,s.room,s.semester,s.year, s.course_id, c.code AS course_code, c.title AS course_title, c.credits, s.instructor_id, s.day_time, s.room, s.capacity, s.semester, s.year FROM sections s JOIN courses c ON s.course_id=c.course_id ";
        List<Section> list=new ArrayList<>();
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query1)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Section s=new Section();
                s.setDayTime(rs.getString("day_time"));
                s.setRoom(rs.getString("room"));
                s.setYear(rs.getInt("year"));
                s.setSemester(rs.getString("semester"));
                s.setCredits(rs.getInt("credits"));
                s.setCourseTitle(rs.getString("course_title"));
                s.setSectionId(rs.getInt("section_id"));
                s.setCourseId(Integer.parseInt(rs.getString("course_id")));
                s.setInstructorId(rs.getInt("instructor_id"));
                s.setCapacity(rs.getInt("capacity"));
                list.add(s);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Section> getSectionsByInstructor(int instructorId){
        List<Section> list = new ArrayList<>();
        String query="SELECT s.section_id, s.course_id, s.instructor_id, s.day_time,s.room, s.capacity, s.semester, s.year, c.title FROM sections s JOIN courses c ON s.course_id=c.course_id WHERE s.instructor_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,instructorId);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Section s=new Section();
                s.setSectionId(rs.getInt("section_id"));
                s.setCourseId(rs.getInt("course_id"));
                s.setCourseTitle(rs.getString("title"));
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

    public List<Section> getSectionsByCourse(int courseCode){
        List<Section> list = new ArrayList<>();
        String query="SELECT section_id, course_id, instructor_id, day_time,room, capacity, semester, year FROM sections WHERE course_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,courseCode);
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

    public void insertSection(Section s) throws SQLException {
        int courseId=0;
        String query1="SELECT course_id FROM courses WHERE code=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query1)){
            stmt.setString(1,s.getCourseCode());
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                courseId=rs.getInt("course_id");
            }
            else{
                throw new SQLException("Course code not found.");
            }
        }


        String query="INSERT INTO SECTIONS (section_id,course_id,semester,day_time,room,capacity,instructor_id,year)"+"VALUES (?,?,?,?,?,?,?,?)";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt= con.prepareStatement(query)){
//            ResultSet rs= stmt.executeQuery();
            stmt.setInt(1,s.getSectionId());
            stmt.setInt(2,courseId);
            stmt.setString(3,s.getSemester());
            stmt.setString(4,s.getDayTime());
            stmt.setString(5,s.getRoom());
            stmt.setInt(6,s.getCapacity());
            stmt.setObject(7,s.getInstructorId());
            stmt.setInt(8,s.getYear());

            stmt.executeUpdate();
        }

    }

    public void updateSection(Section s){
        String query="UPDATE sections SET course_id=?, semester=?, day_time=?, room=?, capacity=?, instructor_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){

            stmt.setInt(1,s.getCourseId());
            stmt.setString(2,s.getSemester());
            stmt.setString(3,s.getDayTime());
            stmt.setString(4,s.getRoom());
            stmt.setInt(5,s.getCapacity());
            stmt.setInt(6,s.getInstructorId());

            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean deleteSection(int sectionId){
        String query="DELETE FROM sections WHERE section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
            stmt.executeUpdate();
//            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Section> getSectionsByCourseId(int courseId) {
        List<Section> list = new ArrayList<>();
        String query="SELECT section_id, course_id, instructor_id, day_time,room, capacity, semester, year FROM sections WHERE course_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,courseId);
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

    public List<Section> getSectionsBySectionId(int sectionId) {
        List<Section> list = new ArrayList<>();
        String query="SELECT section_id, course_id, instructor_id, day_time,room, capacity, semester, year FROM sections WHERE section_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,sectionId);
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

    public List<Section> getSectionsByCourseCode(String code) {
        List<Section> list = new ArrayList<>();
        String query="SELECT s.section_id, s.course_id, s.instructor_id, s.day_time,s.room, s.capacity, s.semester, s.year FROM sections s JOIN courses c ON s.course_id=c.course_id WHERE c.code=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,code);
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

    public List<Section> getAllSections(){
        List<Section> list=new ArrayList<>();
        String query="SELECT * FROM sections";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                list.add(new Section(
                        rs.getInt("section_id"),
                        rs.getInt("course_id"),
                        rs.getInt("instructor_id"),
                        rs.getString("day_time"),
                        rs.getString("room"),
                        rs.getString("semester"),
                        rs.getInt("capacity"),
                        rs.getInt("year")
                ));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
