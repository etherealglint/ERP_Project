package edu.univ.erp.ui.student;

import edu.univ.erp.service.MaintenanceService;
import edu.univ.erp.ui.common.CourseCatalog;
import edu.univ.erp.ui.common.TimeTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentMenu extends JPanel {
    private JFrame frame;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private String checkMaintenance(){
        MaintenanceService m=new MaintenanceService();
        if (m.isMaintenanceEnabled()){
            return m.getMaintenanceMessage();
        }
        return null;
    }

    public StudentMenu(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel sidebar=new JPanel(new MigLayout("wrap 1, gapy 15, insets 20","[grow,fill]",""));
        sidebar.setPreferredSize(new Dimension(250,720));
        sidebar.setBackground(COLOR_DARK);

        JLabel title=new JLabel("Menu");
        title.setFont(new Font("SansSerif",Font.BOLD,22));
        title.setForeground(Color.WHITE);

//        JPanel panelLeft = new JPanel();

        JButton button1 = createSidebarButton("Course catalog");
        JButton button2 = createSidebarButton("Register for courses"); //add and drop both handeled here
        JButton button3 = createSidebarButton("Timetable");
        JButton button4 = createSidebarButton("Grades");
        JButton button5 = createSidebarButton("Home");

//        panelLeft.setPreferredSize(new Dimension(250,720));
//        panelLeft.setBounds(0,0,250,720);
//        panelLeft.setLayout(new GridLayout(5,1));

        sidebar.add(title,"gaptop 10");
        sidebar.add(button1);
        sidebar.add(button2);
        sidebar.add(button3);
        sidebar.add(button4);
        sidebar.add(button5,"push");

        String m=checkMaintenance();
        if(m!=null){
            JLabel label=new JLabel(m);
            label.setFont(new Font("SansSerif",Font.BOLD,15));
            label.setForeground(Color.WHITE);
            sidebar.add(label);
        }

//        frame.add(panelLeft);
//        this.add(panelLeft);
        add(sidebar, BorderLayout.WEST);

        button1.addActionListener(event->{
            frame.setContentPane(new CourseCatalog(frame));
            frame.revalidate();
            frame.repaint();
        });
        button2.addActionListener(event->{
            frame.setContentPane(new CourseRegistration(frame));
            frame.revalidate();
            frame.repaint();
        });
        button3.addActionListener(event->{
            frame.setContentPane(new TimeTable(frame));
            frame.revalidate();
            frame.repaint();
        });
        button4.addActionListener(event->{
            frame.setContentPane(new GradesDisplay(frame));
            frame.revalidate();
            frame.repaint();
        });

        //remove the comment when we shift the main will work then in a cyclic way
        button5.addActionListener(event->{
            frame.setContentPane(new Dashboard(frame));
            frame.revalidate();
            frame.repaint();
        });
    }
    private JButton createSidebarButton(String text){
        JButton btn=new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_VIOLET);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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
}
