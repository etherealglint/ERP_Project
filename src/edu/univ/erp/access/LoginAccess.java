package edu.univ.erp.access;

import edu.univ.erp.auth.session.CurrentSession;

public class LoginAccess {
    public static boolean isLoggedIn(){
        return CurrentSession.getUserId()>0;
    }

    public static boolean isStudent(){
        return "student".equals(CurrentSession.getUser_role());
    }

    public static boolean isAdmin(){
        return "admin".equals(CurrentSession.getUser_role());
    }

    public static  boolean isInstructor(){
        return "instructor".equals(CurrentSession.getUser_role());
    }
}
