package edu.univ.erp.ui.student;

import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.service.EnrollmentService;
import edu.univ.erp.service.GradeService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GradesDisplay extends JPanel {
    private JFrame frame;
    private JTable table;
    private JList<String> enrollmentList;
    private DefaultListModel<String> listModel;
    private DefaultTableModel tableModel;
    private EnrollmentService enrollmentService=new EnrollmentService();
    private GradeService gradeService=new GradeService();
    private List<Enrollment> enrollments;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);


    private void loadGrades(int enrollmentId){
        var grades = gradeService.loadGrades(enrollmentId);
        tableModel.setRowCount(0);
        if(grades==null || grades.isEmpty()){
            tableModel.addRow(new Object[]{"No grades available",""});
            return;
        }
//        for(var g: grades){
//            model.addRow(new Object[]{
//                    g.getQuiz(),
//                    g.getFinalGrade(),
//                    g.getMidSem(),
//                    g.getEndSem(),
//                    g.getAssignment(),
//                    g.getScore()
//            });
//        }
        var g=grades.get(0);
        tableModel.addRow(new Object[]{"Assignment", g.getAssignment()});
        tableModel.addRow(new Object[]{"Mid Sem", g.getMidSem()});
        tableModel.addRow(new Object[]{"End Sem", g.getEndSem()});
        tableModel.addRow(new Object[]{"Quiz", g.getQuiz()});
        tableModel.addRow(new Object[]{"Score", g.getScore()});
        tableModel.addRow(new Object[]{"Final Grade", g.getFinalGrade()});
    }

    private void loadStudentGrades(){
        int studentId=CurrentSession.getUserId();
        EnrollmentService enrollmentService=new EnrollmentService();
        var enrollments=enrollmentService.getEnrollmentsForStudent(studentId);
        if(enrollments.isEmpty()){
            JOptionPane.showMessageDialog(this, "No enrollments found for this student");
            return;
        }
        int enrollmentId=enrollments.get(0).getEnrollmentId();
        loadGrades(enrollmentId);
    }

    public GradesDisplay(JFrame frame){
//        JPanel panel1=new JPanel();
//        JLabel label1=new JLabel("Grades display for students.");
//        panel1.add(new StudentMenu(frame), BorderLayout.WEST);
//        panel1.add(label1,BorderLayout.CENTER);
//        this.add(panel1);
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title=new JLabel("Grades");
        frame.setTitle("Grades");
        title.setFont(new Font("SansSerif",Font.BOLD,20));
        title.setBorder(new EmptyBorder(10,20,10,10));
        title.setForeground(Color.WHITE);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        setSize(800,600);

        StudentMenu menu=new StudentMenu(frame);

        add(menu, BorderLayout.WEST);
//        add(title,BorderLayout.NORTH);

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_PLUM);
        header.setPreferredSize(new Dimension(800,60));
        header.add(title, BorderLayout.WEST);
        add(header,BorderLayout.NORTH);

        JPanel centerPanel=new JPanel(new GridLayout(1,2,20,0));
        centerPanel.setBorder(new EmptyBorder(15,20,20,20));
        centerPanel.setBackground(Color.WHITE);

        listModel=new DefaultListModel<>();
        enrollmentList=new JList<>(listModel);
        enrollmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enrollmentList.addListSelectionListener(e->{
            if(!e.getValueIsAdjusting() && enrollmentList.getSelectedIndex() >=0){
                int i=enrollmentList.getSelectedIndex();
                int enrollmentId=enrollments.get(i).getEnrollmentId();
                loadGrades(enrollmentId);
            }
        });

        enrollmentList.setFont(new Font("SansSerif",Font.PLAIN,15));
        enrollmentList.setSelectionBackground(COLOR_CORAL);
        enrollmentList.setSelectionForeground(Color.WHITE);

        JScrollPane listPane=new JScrollPane(enrollmentList);
        listPane.setBorder(BorderFactory.createTitledBorder("Your Courses"));

        centerPanel.add(listPane);

        String[] coloumns={"components","Grade"};
//        Object[][] data={
//                {"Assignments","A+"},
//                {"MidSem","A+"},
//                {"EndSem","A+"}
//        };
//        model=new DefaultTableModel(data,coloumns);
        tableModel=new DefaultTableModel(coloumns,0);
        JTable table=new JTable(tableModel);
        styleTable(table);

        table.setRowHeight(35);
        centerPanel.add(new JScrollPane(table));
        add(centerPanel,BorderLayout.CENTER);

        loadStudentEnrollments();

//        loadGrades(1001);
//        loadStudentGrades();
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    private void loadStudentEnrollments(){
        int studentId=CurrentSession.getUserId();
        enrollments=enrollmentService.getEnrollmentsForStudent(studentId);
        listModel.clear();
        for(Enrollment e: enrollments){
            listModel.addElement(e.getCourseTitle());
        }
        if(enrollments.isEmpty()){
            JOptionPane.showMessageDialog(this,"No enrollments found for this student");
            return;
        }
//        for(Enrollment e: enrollments){
//            String label=e.getCourseTitle()+"(SEction "+e.getSectionId()+" )";
//            listModel.addElement(label);
//        }
        enrollmentList.setSelectedIndex(0);
        loadGrades(enrollments.get(0).getEnrollmentId());
    }

    private void styleTable(JTable t){
        t.setRowHeight(28);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setFillsViewportHeight(true);
        t.setSelectionBackground(COLOR_CORAL);
        t.setSelectionForeground(Color.WHITE);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD,14));
        t.getTableHeader().setBackground(COLOR_DUSTYRED);
        t.getTableHeader().setForeground(Color.WHITE);
        t.setShowGrid(true);
    }
}
