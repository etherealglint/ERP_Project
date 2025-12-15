package edu.univ.erp.ui.admin;

import edu.univ.erp.data.CourseTable;
import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.data.SectionTable;
import edu.univ.erp.domain.Course;
import edu.univ.erp.ui.student.StudentMenu;
import edu.univ.erp.domain.Section;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CoursesAndSections extends JPanel{
    private JFrame frame;
    private JTable table;
    private JTextField d;
//    JTextField cid=new JTextField(); // for courseId
    private SectionTable sTable=new SectionTable();
    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public CoursesAndSections(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(10,10,10,10));
        setBackground(new Color(245,245,245,245));
//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Courses and Sections modifications and rest info here");
//        panel1.add(new AdminMenu(frame), BorderLayout.CENTER);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);
//        setLayout(new BorderLayout(10,10));
//        setBorder(new EmptyBorder(20,20,20,20));

        add(new AdminMenu(frame),BorderLayout.WEST);

        JPanel topPanel=new JPanel();
//        JButton home=new JButton("home");
//        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(10,10,10,10));
        topPanel.setFont(new Font("SansSerif",Font.BOLD,20));
//        topPanel.add(home,BorderLayout.WEST);
//        home.addActionListener(e->{
//            frame.setContentPane(new AdminDashboard(frame));
//            frame.revalidate();
//            frame.repaint();
//        });
        topPanel.add(Box.createVerticalStrut(10));
        JLabel title=new JLabel("Course and Section Details");
//        topPanel.add(makeFormRow(title,10));
        topPanel.add(title, BorderLayout.WEST);

        JPanel main=new JPanel(new MigLayout("insets 0 0 0 0, wrap 2","[grow,fill][300!]","[]10[grow, fill]"));
        main.setBorder(new EmptyBorder(16,16,16,16));

        JPanel LeftPanel=new JPanel(new MigLayout("wrap 1","[grow, fill]"));
        LeftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PLUM,1),
                new EmptyBorder(12,12,12,12)
        ));
        JLabel courseTitle=new JLabel("Course Details: ");
        courseTitle.setFont(new Font("SansSerif",Font.BOLD,16));
        LeftPanel.add(courseTitle,"wrap");

//        LeftPanel.add(new JLabel("Course ID:"));
//        cid.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
//        LeftPanel.add(cid,"growx, wrap");

        LeftPanel.add(new JLabel("Course code: "));
        JTextField cName=new JTextField();
        cName.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        LeftPanel.add(cName,"growx, wrap");

        LeftPanel.add(new JLabel("Credits: "));
        JTextField credits=new JTextField();
        credits.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        LeftPanel.add(credits,"growx, wrap");

        LeftPanel.add(new JLabel("Course Title: "));
        d=new JTextField();
        d.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        LeftPanel.add(d,"growx, wrap");


        JPanel buttonsPanel=new JPanel();
        JPanel sectionButtonPanel=new JPanel(new FlowLayout());

        JButton newCourse=new JButton("New Course");
        JButton saveCourse=new JButton("Save updated details");
        JButton deleteCourse=new JButton("Delete Course");
        buttonsPanel.add(newCourse,BorderLayout.WEST);
        buttonsPanel.add(saveCourse,BorderLayout.CENTER);
        buttonsPanel.add(deleteCourse,BorderLayout.EAST);

        LeftPanel.add(buttonsPanel,"wrap");


        JPanel bottomPanel=new JPanel(new BorderLayout(20,0));
        JPanel leftPanel=new JPanel(new BorderLayout(10,10));
        leftPanel.setBorder(new EmptyBorder(20,20,20,20));
        leftPanel.add(new JLabel("Sections for <course_name>"));
        String[] coloumns={
                "Section","TT","Capacity","Room"
        };
//        Object[][] data={
//                {"A","MWF","80","A003"},
//                {"B","TTS","60","A003"}
//        };
//        JTable table= new JTable(new DefaultTableModel(coloumns,0));
        table=new JTable(new DefaultTableModel(coloumns,0));
        JScrollPane tableScroll=new JScrollPane(table);
        leftPanel.add(tableScroll,BorderLayout.CENTER);
//        JPanel tableButtons=new JPanel(new FlowLayout(FlowLayout.LEFT,8));
//        JButton addSection=new JButton("New Section");
        JButton delSection=new JButton("Delete Section");
//        tableButtons.add(addSection);
//        tableButtons.add(delSection);

//        sectionButtonPanel.add(addSection);
//        sectionButtonPanel.add(delSection);
        main.add(sectionButtonPanel,"span, split 1, right, wrap");

        JPanel rightPanel=new JPanel(new MigLayout("wrap 1","[grow,fill]"));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PLUM,1),
                new EmptyBorder(12,12,12,12)
        ));
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel sectionTitle=new JLabel("Section Details");
        sectionTitle.setFont(new Font("SansSerif",Font.BOLD,16));
        rightPanel.add(sectionTitle,"wrap");

        rightPanel.add(new JLabel("Section ID"));
        JTextField sid=new JTextField();
        sid.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(sid,"growx, wrap");

        rightPanel.add(new JLabel("Course code"));
        JTextField courseCode=new JTextField();
        courseCode.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(courseCode,"growx, wrap");

        rightPanel.add(new JLabel("Year"));
        JTextField year=new JTextField();
        year.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(year,"growx, wrap");

        rightPanel.add(new JLabel("Semester course offered in"));
        JTextField sem=new JTextField();
        sem.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(sem);

        rightPanel.add(new JLabel("Date Time: "));
        JTextField t=new JTextField();
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(t);

        rightPanel.add(new JLabel("Room alloted"));
        JTextField room=new JTextField();
        room.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(room);

        rightPanel.add(new JLabel("Capacity: "));
        JTextField cap=new JTextField();
        cap.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(cap);

        rightPanel.add(new JLabel("Instructor: "));
        JTextField inst=new JTextField();
        inst.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        rightPanel.add(inst);

        JButton saveNewSection=new JButton("Save new section");
        rightPanel.add(saveNewSection,"align center, wrap");

        rightPanel.add(delSection,"align center, wrap");

        main.add(LeftPanel);
        main.add(rightPanel,"wrap");

//        cid.addActionListener(e->{
//            String id=cid.getText().trim();
//            CourseTable courseTable=new CourseTable();
//            Course c= courseTable.getCourse(id);
////            String code=c
//            if(c!=null){
//                cName.setText(c.getTitle());
//                credits.setText(String.valueOf(c.getCredits()));
//                d.setText(c.getCode());
//            }
//            SectionTable sectionTable=new SectionTable();
//            List<Section> list =sectionTable.getSectionsByCourseId(Integer.parseInt(id));
//            DefaultTableModel model=(DefaultTableModel) table.getModel();
//
//            model.setRowCount(0);
//            for(Section s:list){
//                model.addRow(new Object[]{
//                        s.getSectionId(),
//                        s.getDayTime(),
//                        s.getCapacity(),
//                        s.getRoom()
//                });
//            }
//        });

        saveCourse.addActionListener(e->{
            CourseTable c=new CourseTable();
            if (cName.getText().isEmpty() || d.getText().isEmpty() || credits.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame, "Enter complete course details to update it.");
                return;
            }
            Course nc=new Course(
//                    Integer.parseInt(cid.getText()),
                    cName.getText(),
                    d.getText(),
                    Integer.parseInt(credits.getText())
            );
            c.updateCourse(nc);
            JOptionPane.showMessageDialog(frame,"Course Updated!");
        });

        saveNewSection.addActionListener(e->{

            Section s =new Section();

            s.setSectionId(Integer.parseInt(sid.getText()));
            s.setYear(Integer.parseInt(year.getText()));
            s.setCourseCode(courseCode.getText());
            s.setSemester(sem.getText());
            s.setDayTime(t.getText());
            s.setCapacity(Integer.parseInt(cap.getText()));
            s.setRoom(room.getText());
//            s.setInstructorId(Integer.parseInt(inst.getText()));
            String instId=inst.getText().trim();

            if(year.getText().isEmpty() || courseCode.getText().isEmpty() || sem.getText().isEmpty() || t.getText().isEmpty() || sid.getText().isEmpty() || room.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"Please fill all the fields!");
                return;
            }
            if(cap.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(frame,"Please enter a capacity");
                return;
            }
            if(!cap.getText().matches("\\d+")){
                JOptionPane.showMessageDialog(frame,"Capacity should be a number.");
                return;
            }
            int capValue=Integer.parseInt(cap.getText().trim());
            if (capValue<0){
                JOptionPane.showMessageDialog(frame,"Capacity canot be negative.");
            }

            if(instId.isEmpty()){
                s.setInstructorId(null);
            }
            else{
                s.setInstructorId(Integer.parseInt(instId));
            }

            try{
                sTable.insertSection(s);
                JOptionPane.showMessageDialog(frame,"New Section added! ");
            }
            catch(SQLException e2){
                if ("23000".equals(e2.getSQLState())){
//                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"This section already exists.");
                }
                else{
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"Failed to add section.");
                }
            }


        });

        newCourse.addActionListener(e->{
            CourseTable table1=new CourseTable();
//            String id=cid.getText().trim();
            String title1=cName.getText().trim();
            String code=d.getText().trim();
            String cred=credits.getText().trim();

            if( title1.isEmpty() || code.isEmpty() || cred.isEmpty()){
                JOptionPane.showMessageDialog(frame,"All Fields are required to be filled.");
                return;
            }
            if(!cred.matches("\\d+")){
                JOptionPane.showMessageDialog(frame," credits must be a number ");
                return;
            }
            Course c= new Course(
//                    Integer.parseInt(id),
                    title1,
                    code,
                    Integer.parseInt(cred)
            );

            try{
                table1.insertCourse(c);
                JOptionPane.showMessageDialog(frame, "New Course added!");
            }
            catch(SQLException e1){
                if ("23000".equals(e1.getSQLState())){
//                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"This course already exists.");
                }
                else{
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"Failed to add course.");
                }
            }
            loadSections();
        });

        deleteCourse.addActionListener(e->{
            String code=d.getText().trim();
            if(code.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Enter Course code to delete");
                return;
            }
            int confirm= JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete course "+code+" ? All sections with this course will be deleted too.", "confirm Delete", JOptionPane.YES_NO_OPTION);
            if(confirm!=JOptionPane.YES_OPTION){
                return;
            }
            CourseTable ct=new CourseTable();
            boolean success=ct.deleteCourse(code);

            if(success){
                JOptionPane.showMessageDialog(frame,"Course deleted successfully");
//                cid.setText("");
                cName.setText("");
                credits.setText("");
                d.setText("");

                DefaultTableModel model=(DefaultTableModel) table.getModel();
                model.setRowCount(0);
            }
            else{
                JOptionPane.showMessageDialog(frame,"Failed to delete course");
            }
        });

        delSection.addActionListener(e->{
            String sectionId=sid.getText().trim();
            if(sectionId.isEmpty()){
                JOptionPane.showMessageDialog(frame,"Enter Section ID to delete");
                return;
            }

            EnrollmentTable et=new EnrollmentTable();
            int enrolled=et.countEnrolledStudents(Integer.parseInt(sectionId));
            if(enrolled > 0){
                JOptionPane.showMessageDialog(frame,"Cannot delete this section. There are "+enrolled+" students.");
                return;
            }

            int confirm=JOptionPane.showConfirmDialog(frame,"Delete section"+sectionId+"?","confirm delete",JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION){
                return;
            }
            boolean done=sTable.deleteSection(Integer.parseInt(sectionId));
//            sTable.deleteSection();
            if(done){
                JOptionPane.showMessageDialog(frame,"Section Deleted Successfully !");
            }
            else{
                JOptionPane.showMessageDialog(frame,"Failed to delete section. ");
            }

        });

//        bottomPanel.add(leftPanel,BorderLayout.WEST);
//        bottomPanel.add(rightPanel,BorderLayout.EAST);

        add(main,BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        add(topPanel,BorderLayout.NORTH);
//        add(bottomPanel, BorderLayout.CENTER);
    }

    private void loadSections(){
//        List<Section> list=sTable.getSectionsByCourseId(Integer.parseInt(cid.getText()));
        List<Section> list=sTable.getSectionsByCourseCode(d.getText());
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for(Section s:list){
            model.addRow(new Object[]{
                    s.getSectionId(),
                    s.getDayTime(),
                    s.getCapacity(),
                    s.getRoom()
            });
        }
    }
}
