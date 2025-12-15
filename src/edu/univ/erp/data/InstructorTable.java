package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InstructorTable {
    public void insertInstructor(int userId, String department, String designation){
        String query="INSERT INTO instructors (user_id, department, designation) VALUES (?,?,?)";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,userId);
            stmt.setString(2,department);
            stmt.setString(3,designation);
            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
