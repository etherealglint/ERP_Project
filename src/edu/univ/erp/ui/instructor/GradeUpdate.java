package edu.univ.erp.ui.instructor;

import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.data.EnrollmentTable;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Grade;
import edu.univ.erp.domain.Section;
import edu.univ.erp.service.GradeService;
import edu.univ.erp.service.GradeUpdateService;
import edu.univ.erp.service.InstructorSectionService;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.math.BigDecimal;

public class GradeUpdate extends JPanel{
    private JFrame frame;
    private JTable table;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private JTextField comp1;
    private JTextField comp2;
    private JTextField comp3;
    private JTextField comp4;

    public GradeUpdate(JFrame frame){
        this.frame=frame;
//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Update Grades here");
//        panel1.add(new InstructorMenu(frame), BorderLayout.WEST);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(10,10,10,10));
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        topPanel.setBackground(COLOR_PLUM);
        topPanel.setBorder(new EmptyBorder(10,10,10,10));

        JButton home=styleButton("Home");
        topPanel.add(home,BorderLayout.WEST);
        home.addActionListener(e->{
            frame.setContentPane(new InstructorDashboard(frame));
            frame.repaint();
            frame.revalidate();
        });

//        JComboBox<String> courseTable=new JComboBox<>(new String[]{
//                "CSE 101", "MTH-101"
//        });
//        JComboBox<String> sectionTable=new JComboBox<>(new String[]{
//                "A","B"
//        });

        InstructorSectionService sectionService=new InstructorSectionService();
        int instructorId= CurrentSession.getUserId();
        JComboBox<Section> sectionTable=new JComboBox<>();
        sectionTable.setBackground(COLOR_VIOLET);
        sectionTable.setForeground(Color.white);

        List<Section> mySections=sectionService.getMySections(instructorId);
//        sectionTable=sectionService.getMySections(instructorId);
        for (Section s:mySections){
            sectionTable.addItem(s);
        }

        JButton loadButton =styleButton("Load Students");
//        topPanel.add(styledLabel("Course: "));
//        topPanel.add(courseTable);
        topPanel.add(styledLabel("Section: "));
        topPanel.add(sectionTable);
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel=new JPanel(new BorderLayout(5,5));
        leftPanel.setBackground(COLOR_DARK);
        leftPanel.setBorder(new EmptyBorder(10,10,10,10));

//        String[] coloumns={"Roll No.", "Name", "Assignments(20%)","Final Exam(40%)","Mid Exam(30%)"};
//        Object[][] data={
//                {"2024111","ABC",20,40,30},
//                {"2024211","ABC",19,30,30}
//        };

        table=new JTable(new DefaultTableModel(new Object[][]{},new String[]{"Roll No","Name","Assignmnets","Mid Sem","End Sem","Quiz"}));
        table.setBackground(COLOR_DUSTYRED);
        table.setForeground(Color.WHITE);

        JScrollPane tableScroll= new JScrollPane(table);
        leftPanel.add(tableScroll,BorderLayout.CENTER);

        JPanel rightPanel=new JPanel();
        rightPanel.setLayout(new GridLayout(6,2,5,5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Grading components"));
        rightPanel.setBackground(COLOR_PLUM);
        rightPanel.setForeground(Color.WHITE);

        rightPanel.add(styledLabel("Assignment:"));
        comp1=styledField("20%");
        rightPanel.add(comp1);
        rightPanel.add(styledLabel("Final :"));
        comp2=styledField("40%");
        rightPanel.add(comp2);
        rightPanel.add(styledLabel("Mid :"));
        comp4=styledField("30%");
        rightPanel.add(comp4);
        rightPanel.add(styledLabel("Quiz: "));
        comp3=styledField("10%");
        rightPanel.add(comp3);

        rightPanel.add(styledLabel("Passing threshold: "));
        JTextField threshold=styledField("40%");
        rightPanel.add(threshold);

        JButton compute=styleButton("Compute Final Grades");
        rightPanel.add(compute);

        compute.addActionListener(e->{
            String m1=computeFinalGrades();
            JOptionPane.showMessageDialog(this,m1);
        });

        JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel, rightPanel);
        splitPane.setDividerLocation(650);
        splitPane.setBackground(COLOR_DARK);
        add(splitPane, BorderLayout.CENTER);

        JPanel footer=new JPanel(new BorderLayout());
        footer.setBackground(COLOR_VIOLET);
        footer.setBorder(new EmptyBorder(10,10,10,10));

        JLabel status=styledLabel("Status: Grades Loaded for <course> and ready for changes ");
        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(COLOR_PLUM);

        JButton save = styleButton("Save changes");
        JButton cancel=styleButton("Cancel changes");
        buttonPanel.add(save);
        buttonPanel.add(cancel);

        save.addActionListener(e->{

            String msg=computeFinalGrades();
            JOptionPane.showMessageDialog(this,msg);

        });

        loadButton.addActionListener(e->{
            Section selected=(Section) sectionTable.getSelectedItem();
            if(selected != null){
                String courseName=selected.getCourseTitle();
                status.setText("Status : Grades Loaded for "+courseName+" and ready for changes.");
                loadStudentsForSection(selected.getSectionId());
            }
        });

        footer.add(status,BorderLayout.WEST);
        footer.add(buttonPanel);
        add(footer, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadStudentsForSection(int sectionId){
        GradeService service=new GradeService();
        List<Grade> grades = service.loadGradesForSection(sectionId);
        String[] coloumns={"Enrollment Id","Name","Assignment","Mid Sem","End sem","Quiz"};
        Object[][] data=new Object[grades.size()][6];
        for(int i=0; i<grades.size(); i++){
            Grade g=grades.get(i);
            data[i][0]=g.getEnrollmentId();
            data[i][1]=g.getName();
            data[i][2]=g.getAssignment();
            data[i][3]=g.getMidSem();
            data[i][4]=g.getEndSem();
            data[i][5]=g.getQuiz();
        }
        table.setModel(new DefaultTableModel(data,coloumns));
    }

    private JLabel styledLabel(String text){
        JLabel l=new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }
    private JTextField styledField(String text){
        JTextField f=new JTextField(text);
        f.setBackground(COLOR_VIOLET);
        f.setForeground(Color.WHITE);
        return f;
    }

    private JButton styleButton(String text){
        JButton btn=new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_VIOLET);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event){
                btn.setBackground(COLOR_CORAL);
            }
            public void mouseExited(MouseEvent event){
                btn.setBackground(COLOR_VIOLET);
            }
        });
        return btn;
    }

    private String computeFinalGrades(){
        int row=table.getSelectedRow();
        if (row==-1){
            return "No data available.";
        }

        Grade g =new Grade();
        g.setEnrollmentId((Integer)table.getValueAt(row,0));
        g.setAssignment(Double.parseDouble(table.getValueAt(row,2).toString()));
        g.setMidSem(Double.parseDouble(table.getValueAt(row,3).toString()));
        g.setEndSem(Double.parseDouble(table.getValueAt(row,4).toString()));
        g.setQuiz(Double.parseDouble(table.getValueAt(row,5).toString()));

        double assignmentWt=Double.parseDouble(comp1.getText().replace("%",""))/100.0;
        double midSemWt=Double.parseDouble(comp4.getText().replace("%",""))/100.0;
        double endSemWt=Double.parseDouble(comp2.getText().replace("%",""))/100.0;
        double quizWt=Double.parseDouble(comp3.getText().replace("%",""))/100.0;

        GradeUpdateService gService=new GradeUpdateService();
        String msg=gService.computeAndSave(g,assignmentWt,midSemWt,endSemWt,quizWt);
        return msg;

    }
}
