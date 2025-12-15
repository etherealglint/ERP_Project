package edu.univ.erp.service;

import edu.univ.erp.data.InstructorTable;
import edu.univ.erp.data.StudentTable;
import edu.univ.erp.data.UsersTable;

public class AddUserService {

    private UsersTable userTable=new UsersTable();
    private InstructorTable instructorTable=new InstructorTable();
    private StudentTable studentTable=new StudentTable();

    public String addStudentUser(String username, String password, String rollNo, String program, int year){
        if (userTable.userNameExists(username)){
            return "This user_id already exists";
        }
        int user_id=userTable.insertUser(username,password,"student");
        if(user_id==0){
            return "Failed to create user";
        }
        studentTable.insertStudent(user_id,rollNo,program,year);
        return "Student created successfully";

    }

    public String addInstructorUser(String username, String password, String department, String designation){
        if (userTable.userNameExists(username)){
            return "THis user_id already exists";
        }
        int user_id=userTable.insertUser(username,password,"instructor");
        if(user_id==0){
            return "Failed to create user";
        }
        instructorTable.insertInstructor(user_id,department,designation);
        return "Instructor created successfully";
    }

    public String addAdminUser(String username, String password){
        if (userTable.userNameExists(username)){
            return "THis user_id already exists";
        }
        int user_id=userTable.insertUser(username,password,"admin");
        if(user_id==0){
            return "Failed to create user";
        }
        return "Admin created successfully";
    }
}
