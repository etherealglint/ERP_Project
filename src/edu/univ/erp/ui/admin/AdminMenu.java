package edu.univ.erp.ui.admin;

import edu.univ.erp.service.MaintenanceService;
import edu.univ.erp.ui.instructor.InstructorDashboard;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMenu extends JPanel{
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

    public AdminMenu(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(COLOR_DARK);

        JPanel sidebar=new JPanel(new MigLayout("wrap 1, gapy 15, inset 20","[grow,fill]"));
        sidebar.setBackground(COLOR_DARK);

        JLabel title=new JLabel("Admin Menu");
        title.setFont(new Font("SansSerif",Font.BOLD,20));
        title.setForeground(Color.WHITE);
        sidebar.add(title,"gapy 10");

        JButton button1=createButton("Home");
        JButton button2=createButton("Add Users");
        JButton button3=createButton("Course and Section");
        JButton button4=createButton("Maintenance");
        JButton button5=createButton("Instructor Allotment");
//        JPanel panel= new JPanel();

        sidebar.add(button1);
        sidebar.add(button2);
        sidebar.add(button3);
        sidebar.add(button4);
        sidebar.add(button5);

        String m=checkMaintenance();
        if(m!=null){
            JLabel label=new JLabel(m);
            label.setFont(new Font("SansSerif",Font.BOLD,22));
            label.setForeground(Color.WHITE);
            sidebar.add(label);
        }

        button1.addActionListener(event->{
            frame.setContentPane(new AdminDashboard(frame));
            frame.revalidate();
            frame.repaint();
        });
//
        button2.addActionListener(event->{
            frame.setContentPane(new AddUsers(frame));
            frame.revalidate();
            frame.repaint();
        });
        button3.addActionListener(event->{
            frame.setContentPane(new CoursesAndSections(frame));
            frame.revalidate();
            frame.repaint();
        });
        button4.addActionListener(event->{
            frame.setContentPane(new MaintenanceMode(frame));
            frame.revalidate();
            frame.repaint();
        });
        button5.addActionListener(event->{
            frame.setContentPane(new InstructorAllotment(frame));
            frame.revalidate();
            frame.repaint();
        });

//        panel.setPreferredSize(new Dimension(250,720));
//        panel.setLayout(new GridLayout(5,1));
        this.add(sidebar, BorderLayout.WEST);
    }

    private JButton createButton(String text){
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
