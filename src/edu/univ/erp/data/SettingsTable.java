package edu.univ.erp.data;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SettingsTable {
    public boolean isMaintenanceMode(){
        String query="SELECT setting_value FROM settings WHERE setting_key='maintenance_mode' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
            PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                return rs.getString("setting_value").equalsIgnoreCase("true");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean getMaintenanceEnabled(){
        String query="SELECT setting_value FROM settings WHERE setting_key='maintenance_mode' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                return Boolean.parseBoolean(rs.getString("setting_value"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getMaintenanceMessage(){
        String query="SELECT setting_value FROM settings WHERE setting_key='maintenance_message'";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                return rs.getString("setting_value");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void setMaintenanceEnabled(boolean enabled){
        String query="UPDATE settings SET setting_value=? WHERE setting_key='maintenance_mode'";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,String.valueOf(enabled));
            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setMaintenanceMessage(String msg){
        String query="UPDATE settings SET setting_value=? WHERE setting_key='maintenance_message' ";
        try(Connection con=ERPDatabaseConnection.getConnection();
        PreparedStatement stmt=con.prepareStatement(query)){
            stmt.setString(1,msg);
            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

//    public String getSetting(String Key){
//        String query="SELECT value FROM settings WHERE `key`=?";
//        try(Connection con=ERPDatabaseConnection.getConnection();
//            PreparedStatement stmt=con.prepareStatement(query)){
//            ResultSet rs=stmt.executeQuery();
//            if(rs.next()){
//                return rs.getString("value").equalsIgnoreCase("true");
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public String checkDropEligibility(){
//        String query="SELECT setting_value FROM settings WHERE setting_key='drop_deadline' ";
//        try(Connection con=ERPDatabaseConnection.getConnection();
//        PreparedStatement stmt= con.prepareStatement(query)){
//            ResultSet rs=stmt.executeQuery();
//            if(!(rs.next())){
//                return "Drop deadline not set by admin";
//            }
//            String deadline=rs.getString("setting_value");
//            long deadlineEpoch=Long.parseLong(deadline);
//            long now=System.currentTimeMillis();
//            if(now>deadlineEpoch){
//                return "Drop deadline has passed.";
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        return "OK";
//    }
}


