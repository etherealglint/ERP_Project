package edu.univ.erp.ui.common;

import edu.univ.erp.access.LoginAccess;
import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.data.SectionTable;
import edu.univ.erp.domain.Section;
import edu.univ.erp.ui.admin.AdminMenu;
import edu.univ.erp.ui.instructor.InstructorMenu;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TimeTable extends JPanel{
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    public TimeTable(){
        JLabel title=new JLabel("Time table");
        setSize(800,600);
        setLayout(new BorderLayout());
//        panel1.add(new StudentMenu(frame), BorderLayout.WEST);
//        panel1.add(label1, BorderLayout.CENTER);
//        this.add(panel1);

//        String role= CurrentSession.getUser_role();
//        if(LoginAccess.isStudent()){
//            add(new StudentMenu(frame),BorderLayout.WEST);
//        }
//        else if(LoginAccess.isInstructor()){
//
//        }
//        else if(LoginAccess.isAdmin()){
//            add(new AdminMenu(frame), BorderLayout.WEST );
//        }

//        this.frame=frame;
//        frame.setTitle("TimeTable");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        StudentMenu menu=new StudentMenu(frame);
//        add(menu,BorderLayout.WEST);
        add(title,BorderLayout.NORTH);

        String[] coloumns={"Time", "Course","Room","Semester","Year"};
//        Object[][] data={
//                {"10:00","Lecture","CSE101","Oct 20 2025"},
//                {"13:00","Lab","ECE111","Oct 20 2025"}
//        };
        model=new DefaultTableModel(coloumns,0);
        JTable table=new JTable(model);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setFont(new Font("SansSerif",Font.PLAIN,14));

        add(new JScrollPane(table),BorderLayout.CENTER);

        SectionTable sectionTable=new SectionTable();
        if(LoginAccess.isStudent()){
            int studentId=CurrentSession.getUserId();
            EnrollmentTable t=new EnrollmentTable();
            List<Section> sections=t.getStudentEnrolledCourses(studentId);
            display(sections);
        }
        else if(LoginAccess.isInstructor()){
            int instructorId=CurrentSession.getUserId();
            List<Section> sections=sectionTable.getSectionsByInstructor(instructorId);
            display(sections);
        }
        else if(LoginAccess.isAdmin()){
            List<Section> sections=sectionTable.getCatalog();
            display(sections);
        }

//        frame.setVisible(true);
    }
    public TimeTable(JFrame frame){
//        this.frame=frame;
//        JPanel panel1=new JPanel();
        JLabel title=new JLabel("Time table");
        setSize(900,600);
        setLayout(new BorderLayout());
//        panel1.add(new StudentMenu(frame), BorderLayout.WEST);
//        panel1.add(label1, BorderLayout.CENTER);
//        this.add(panel1);

//        String role= CurrentSession.getUser_role();
        if(LoginAccess.isStudent()){
            add(new StudentMenu(frame),BorderLayout.WEST);
        }
        else if(LoginAccess.isInstructor()){
            add(new InstructorMenu(frame),BorderLayout.WEST);
        }
        else if(LoginAccess.isAdmin()){
            add(new AdminMenu(frame), BorderLayout.WEST );
        }

        this.frame=frame;
        frame.setTitle("TimeTable");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        StudentMenu menu=new StudentMenu(frame);
//        add(menu,BorderLayout.WEST);
        add(title,BorderLayout.NORTH);

        String[] coloumns={"Time", "Course","Room","Semester","Year"};
//        Object[][] data={
//                {"10:00","Lecture","CSE101","Oct 20 2025"},
//                {"13:00","Lab","ECE111","Oct 20 2025"}
//        };
        model=new DefaultTableModel(coloumns,0);
        JTable table=new JTable(model);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setFont(new Font("SansSerif",Font.PLAIN,14));

        add(new JScrollPane(table),BorderLayout.CENTER);

        SectionTable sectionTable=new SectionTable();
        if(LoginAccess.isStudent()){
            int studentId=CurrentSession.getUserId();
            EnrollmentTable t=new EnrollmentTable();
            List<Section> sections=t.getStudentEnrolledCourses(studentId);
            display(sections);
        }
        else if(LoginAccess.isInstructor()){
            int instructorId=CurrentSession.getUserId();
            List<Section> sections=sectionTable.getSectionsByInstructor(instructorId);
            display(sections);
        }
        else if(LoginAccess.isAdmin()){
            List<Section> sections=sectionTable.getAllSections();
            display(sections);
        }


        frame.setVisible(true);
    }

    private void display(List<Section> s){
        for (Section section: s){
            model.addRow(new Object[]{
                    section.getDayTime(),
                    section.getCourseTitle(),
                    section.getRoom(),
                    section.getSemester(),
                    section.getYear()
            });
        }
    }
}
