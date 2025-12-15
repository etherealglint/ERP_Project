package edu.univ.erp.ui.instructor;

import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Section;
import edu.univ.erp.service.InstructorSectionService;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Sections extends JPanel{
    private JFrame frame;
    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private JLabel currentCourse;
    private JLabel totalStudents;
    private JTable gradeTable;
    public Sections(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        //select students enrolled in that particular instuctor's course and then get the students ans then update their scores.

//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Sections details here");
//        panel1.add(new StudentMenu(frame), BorderLayout.WEST);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_PLUM);
        header.setBorder(new EmptyBorder(10,15,10,15));

        JButton home = new JButton("Home");
        home.setForeground(Color.WHITE);
        home.setBackground(COLOR_CORAL);
        home.setFocusPainted(false);

        JLabel title=new JLabel("Sections Management");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD, 20));

        JLabel instructorName=new JLabel("Instructor Name");
        instructorName.setForeground(Color.WHITE);

        JButton save=new JButton("Save changes");
        save.setBackground(COLOR_CORAL);
        save.setForeground(Color.WHITE);

        JPanel rightHeader=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        rightHeader.add(instructorName);
        rightHeader.add(save);

        home.addActionListener(e->{
            frame.setContentPane(new InstructorDashboard(frame));
            frame.repaint();
            frame.revalidate();
        });

        header.add(home,BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.add(rightHeader, BorderLayout.EAST);

        add(header,BorderLayout.NORTH);

        //Left side
        JPanel leftSide=new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide,BoxLayout.Y_AXIS));
        leftSide.setPreferredSize(new Dimension(280,0));
        leftSide.setBorder(new EmptyBorder(20,20,20,20));
        leftSide.setBackground(COLOR_DARK);

        JLabel section=new JLabel("Course Sections");
        section.setFont(new Font("Segoe UI",Font.BOLD,16));
        section.setForeground(Color.WHITE);

        JLabel selectSection=new JLabel("Select a section: ");
//        JComboBox<String> sectionTable=new JComboBox<>(
//                new String[]{"MTH-211 Sec A","CSE-121 sec-A"}
//        );
        InstructorSectionService service=new InstructorSectionService();
        int instructorId= CurrentSession.getUserId();
        List<Section> mySections=service.getMySections(instructorId);

        JComboBox<Section> sectionTable=new JComboBox<>();
//        for(Section s: mySections){
//            sectionTable.addItem("Course "+s.getCourseId()+"/ Section "+s.getSectionId());
//        }

        for(Section s:mySections){
            sectionTable.addItem(s);
        }
        sectionTable.setMaximumSize(new Dimension(200,30));
        sectionTable.setBackground(Color.WHITE);

        sectionTable.addActionListener(e->{
            Section selected=(Section) sectionTable.getSelectedItem();
            if(selected!=null){
                loadStudentsForSection(selected.getSectionId());
            }
        });

        currentCourse=new JLabel("Current Course: ");
        totalStudents=new JLabel("Total Students: ");
        totalStudents.setForeground(Color.WHITE);
        currentCourse.setForeground(Color.WHITE);

        leftSide.add(section);
        leftSide.add(Box.createVerticalStrut(15));
        leftSide.add(selectSection);
        leftSide.add(sectionTable);
        leftSide.add(Box.createVerticalStrut(30));
        leftSide.add(currentCourse);
        leftSide.add(Box.createVerticalStrut(10));
        leftSide.add(totalStudents);

        add(leftSide, BorderLayout.WEST);

        JPanel center=new JPanel(new BorderLayout());
        center.setBackground(Color.white);
        center.setBorder(new EmptyBorder(20,20,20,20));

//        JLabel updateGrades=new JLabel("Update Grades for <course name>");
//        updateGrades.setFont(new Font("Segoe UI", Font.BOLD,18));
        //        String[] coloumns={"Student Name","Quiz 1","Assignments","Mid-sem","End-sem"};
//        Object[][] data={
//                {"ABC","20","40","20","40"},
//                {"smaple_2","15","18","20","30"}
//        };

        gradeTable=new JTable();
        gradeTable.setRowHeight(28);
        gradeTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane tableScroll=new JScrollPane(gradeTable);
//        center.add(updateGrades,BorderLayout.NORTH);
        center.add(tableScroll, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        if(!mySections.isEmpty()){
            Section first=mySections.get(0);
            loadStudentsForSection(first.getSectionId());
        }

        sectionTable.addActionListener(e->{
            Section selected=(Section) sectionTable.getSelectedItem();
            if (selected!=null){
                loadStudentsForSection(selected.getSectionId());
            }
        });


    }
    private void loadStudentsForSection(int sectionId){
        List<Enrollment> students = new EnrollmentTable().getEnrollmentsForSection(sectionId);
        String[] coloumns={"Roll No","Name","Quiz","Assignment","Mid Sem","End sem"};
        Object[][] data=new Object[students.size()][6];
        for(int i=0; i<students.size(); i++){
            Enrollment e=students.get(i);
            data[i][0]=e.getRollNo();
            data[i][1]=e.getName();
            data[i][2]=e.getQuiz();
            data[i][3]=e.getAssignment();
            data[i][4]=e.getMidSem();
            data[i][5]=e.getEndSem();
        }
        gradeTable.setModel(new DefaultTableModel(data,coloumns));
        currentCourse.setText("Current Course: Section "+sectionId);
        totalStudents.setText("Total Students: "+students.size());
    }
}
