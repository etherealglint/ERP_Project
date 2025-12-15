package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentTable {

    public void insertStudent(int userId, String rollNo, String program, int year){
        String query="INSERT INTO students (user_id,roll_no,program,year) VALUES (?,?,?,?)";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,userId);
            stmt.setString(2,rollNo);
            stmt.setString(3,program);
            stmt.setInt(4,year);

            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
