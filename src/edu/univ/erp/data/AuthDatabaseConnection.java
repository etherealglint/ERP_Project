package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthDatabaseConnection {
    private static final String URL="jdbc:mysql://localhost:3306/AUTH_DB";
    private static final String USER="root";
    private static final String PASSWORD="Ishika@20225";

    private static final String URL_test="jdbc:mysql://localhost:3306/AUTH_TEST_DB";
    private static final String USER_test="root";
    private static final String PASSWORD_test="Ishika@20225";
    private static boolean testMode=false;
    public static void enableTestMode(){
        testMode=true;
    }
    public static Connection getConnection(){
//        Connection con = null;
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con= DriverManager.getConnection(URL,USER,PASSWORD);
//            System.out.println("Connected to db");
//        }
//        catch(ClassNotFoundException | SQLException e){
//            e.printStackTrace();
//        }
//        return con;
        try{
            if(testMode){
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(URL_test,USER_test,PASSWORD_test);
            }
            else{
                return DriverManager.getConnection(URL,USER,PASSWORD);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection con){
        if(con != null){
            try{
                con.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
