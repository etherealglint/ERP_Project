package edu.univ.erp.data;

import edu.univ.erp.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseTable {

    public boolean insertCourse(Course c) throws SQLException {
        String query="INSERT INTO courses (code,title,credits) VALUES (?,?,?)";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){

//            stmt.setInt(1,c.getCourse_id());
            stmt.setString(1,c.getCode());
            stmt.setString(2,c.getTitle());
            stmt.setInt(3,c.getCredits());

            return stmt.executeUpdate()>0;
        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        return false;
    }

    public boolean deleteCourse(String courseCode){
        //first i have to find out the courseId from course code
        int courseId=-1;
        String fetchId="SELECT course_id FROM courses WHERE code=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(fetchId)){
            stmt.setString(1,courseCode);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                courseId=rs.getInt("course_id");
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        String query="DELETE FROM courses WHERE code=?";
        String query2="DELETE FROM sections WHERE course_id=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query2)){
            stmt.setInt(1,courseId);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,courseCode);
            int rows= stmt.executeUpdate();
            return rows>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCourse(Course c){
        String query="UPDATE courses SET title=?, credits=? WHERE code=?";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){

//            stmt.setInt(4,c.getCourse_id());
            stmt.setString(3,c.getCode());
            stmt.setString(1,c.getTitle());
            stmt.setInt(2,c.getCredits());

            return stmt.executeUpdate()>0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Course> getAllCourses(){
        List<Course> courseList=new ArrayList<>();
        String query="SELECT course_id, code, title, credits FROM courses";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Course c=new Course(
                        rs.getInt("course_id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getInt("credits")
                );
                courseList.add(c);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return courseList;
    }
}
