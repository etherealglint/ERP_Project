package edu.univ.erp.ui.instructor;

import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.service.MaintenanceService;
import edu.univ.erp.ui.auth.Login;
import edu.univ.erp.ui.common.TimeTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InstructorMenu extends JPanel{
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

    public InstructorMenu(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(COLOR_DARK);

        JPanel sidebar= new  JPanel(new MigLayout("wrap 1, gapy 15, inset 20","[grow,fill]"));
        sidebar.setBackground(COLOR_DARK);

        JLabel title=new JLabel("Instructor Menu");
        title.setFont(new Font("SansSerif",Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        sidebar.add(title,"gapy 10");

        JButton button1=createSideBarButton("Home");
        JButton button2=createSideBarButton("Sections");
        JButton button3=createSideBarButton("Grade Update");
        JButton button4=createSideBarButton("Class Statistics");
        JButton button5=createSideBarButton("Time table");
        JButton logout=createSideBarButton("Logout");

        logout.addActionListener(e->{
            frame.setContentPane(new Login(frame));
            frame.revalidate();
            frame.repaint();
        });
        button1.addActionListener(event->{
            frame.setContentPane(new InstructorDashboard(frame));
            frame.revalidate();
            frame.repaint();
        });
        button2.addActionListener(event->{
            frame.setContentPane(new Sections(frame));
            frame.revalidate();
            frame.repaint();
        });
        button3.addActionListener(event->{
            frame.setContentPane(new GradeUpdate(frame));
            frame.revalidate();
            frame.repaint();
        });
        button4.addActionListener(event->{
            int id= CurrentSession.getUserId();
            frame.setContentPane(new ClassStats(frame,id));
            frame.revalidate();
            frame.repaint();
        });
        button5.addActionListener(event->{
            frame.setContentPane(new TimeTable(frame));
            frame.revalidate();
            frame.repaint();
        });
        sidebar.setPreferredSize(new Dimension(250,720));
//        sidebar.setLayout(new GridLayout(4,1));
        sidebar.add(button1);
        sidebar.add(button2);
        sidebar.add(button3);
        sidebar.add(button4);
        sidebar.add(button5);
        sidebar.add(logout);

        String m=checkMaintenance();
        if(m!=null){
            JLabel label=new JLabel(m);
            label.setFont(new Font("SansSerif",Font.BOLD,15));
            label.setForeground(Color.WHITE);
            sidebar.add(label);
        }

        this.add(sidebar, BorderLayout.WEST);
    }

    private JButton createSideBarButton(String text){
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
}
