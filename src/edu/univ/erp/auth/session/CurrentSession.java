package edu.univ.erp.auth.session;

public class CurrentSession {
    private static int user_id;
    private static String username;
    private static String user_role;

    public static void setUser(int id, String user_name, String role){
        user_id=id;
        username=user_name;
        user_role=role;
    }
    public static int getUserId(){
        return user_id;
    }
    public static String getUsername(){
        return username;
    }
    public static String getUser_role(){
        return user_role;
    }
}
