package edu.univ.erp.data;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsersTable  {

    public int insertUser(String username, String password, String role){
        password= BCrypt.hashpw(password, BCrypt.gensalt());
        String query="INSERT INTO users_auth (username, password_hash, role, status, last_login) VALUES(?,?,?,'active',NULL)";
        try(Connection con=AuthDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
//            stmt.setInt(1,userId);
            stmt.setString(1,username);
            stmt.setString(3,role);
            stmt.setString(2,password);
            int rows=stmt.executeUpdate();
            if(rows==0){
                return 0;
            }
            ResultSet rs=stmt.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }

//            stmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
//            return 0;
        }
        return 0;
    }

    public boolean userNameExists(String username){
        String query="SELECT 1 FROM users_auth WHERE username=?";
        try(Connection con=AuthDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,username);
            return stmt.executeQuery().next();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
