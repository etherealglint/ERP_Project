package edu.univ.erp.auth.store;

import edu.univ.erp.data.AuthDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


public class AuthStore {
    public boolean validateUser(String username, String password){
        String query="SELECT password_hash FROM users_auth WHERE username= ?";
        try(Connection con= AuthDatabaseConnection.getConnection(); PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,username);
            ResultSet r=stmt.executeQuery();
            if(r.next()){
                String storedHash=r.getString("password_hash");
                System.out.println("DB USERNAME ROW FOUND FOR = [" + username + "]");

                System.out.println("Comparing password...");
                System.out.println("Input password = [" + password + "]");
                System.out.println("Stored hash = [" + storedHash + "]");
                System.out.println("Length = " + storedHash.length());

                return BCrypt.checkpw(password, storedHash);
//                return password.equals(storedHash); //for the string matching the database one, not the hashed one

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public String getUserRole(String username){
        String query="SELECT role FROM users_auth WHERE username = ?";
        try(Connection con=AuthDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,username);
            ResultSet r=stmt.executeQuery();
            if (r.next()) {
                return r.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getUserId(String username){
        String query="SELECT user_id FROM users_auth WHERE username = ?";
        try(Connection con=AuthDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,username);
            ResultSet r=stmt.executeQuery();
            if(r.next()){
                return r.getInt("user_id");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public void updateLastLogin(int user_id){
        String query="UPDATE users_auth SET last_login=NOW() WHERE user_id=?";
        try(Connection con= AuthDatabaseConnection.getConnection();
           PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setInt(1,user_id);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
