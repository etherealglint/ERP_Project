package edu.univ.erp.ui.common;

import edu.univ.erp.domain.Section;
import edu.univ.erp.service.CourseRegestrationService;
import edu.univ.erp.ui.student.CourseRegistration;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CourseCatalog extends JPanel {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public CourseCatalog(JFrame frame){
        this.frame=frame;
//        JPanel panel1= new JPanel();
        JLabel title= new JLabel("Complete Course info here");
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));
        title.setFont(new Font("Arial",Font.BOLD,16));
        //        panel1.add(new StudentMenu(frame), BorderLayout.CENTER);
//        panel1.add(title, BorderLayout.CENTER);

        frame.setTitle("Course catalog");
        setBackground(Color.WHITE);
        setSize(800,600);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //menu depends on the user
        StudentMenu menu=new StudentMenu(frame);
        menu.setPreferredSize(new Dimension(250,0));

        add(menu, BorderLayout.WEST);
//        add(title, BorderLayout.NORTH); we will add the title in a header

        JPanel header=new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(800,60));
        header.setBackground(COLOR_PLUM);
        header.add(title, BorderLayout.WEST);
        add(header,BorderLayout.NORTH);

        String[] columns={"Code","Title","Capacity","Instructor","Credits"};
//        Object[][] data={
//                {"CSE101","IP",300,"ABC"},
//                {"MTH101","LA",300,"ABC"}
//        };
        model = new DefaultTableModel(columns,0){
            //override here for admin to edit some cells like capacity and instructor
            //for table sorting add here after overriding
        };

        loadCatalog();

        JPanel controlPanel=new JPanel();
        this.add(controlPanel, BorderLayout.NORTH);

        table=new JTable(model);
        styleTable(table);
//        table.setRowHeight(28);
//        table.setGridColor(Color.LIGHT_GRAY);
        //can add a selection mode for students
//        table.setFillsViewportHeight(true);

        this.add(new JScrollPane(table),BorderLayout.CENTER);
//        this.add(panel1);

        frame.setContentPane(this);
        frame.setVisible(true);
    }

    private void loadCatalog(){
        model.setRowCount(0);
        CourseRegestrationService service=new CourseRegestrationService();
        List<Section> catalog=service.getCatalog();
        for(Section s: catalog){
            model.addRow(new Object[]{
//                    s.getSectionId(),
                    s.getCourseId(),
                    s.getCourseTitle(),
                    s.getCapacity(),
                    s.getInstructorId(),
                    s.getCredits()
            });
        }
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

        JTableHeader header=t.getTableHeader();
        header.setFont(new Font("SansSerif",Font.BOLD,14));
        header.setBackground(COLOR_DUSTYRED);
        header.setForeground(Color.WHITE);
    }
}
