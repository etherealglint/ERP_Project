package edu.univ.erp.ui.admin;

import edu.univ.erp.access.LoginAccess;
import edu.univ.erp.ui.auth.Login;
import edu.univ.erp.ui.common.TimeTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JPanel{
    private JFrame frame;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public AdminDashboard(JFrame frame){

        if(!LoginAccess.isAdmin()){
            JOptionPane.showMessageDialog(frame,"Access Denied");
            return;
        }
//        JFrame frame = new JFrame();
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_DARK);
        header.setPreferredSize(new Dimension(1280,60));
        header.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title=new JLabel("Admin Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD,22));
        title.setForeground(Color.WHITE);

        JPanel headerRight=new JPanel(new FlowLayout(FlowLayout.RIGHT,15,5));
        headerRight.setBackground(COLOR_DARK);

        JButton profile=styleButton("Profile");
        JButton logout=styleButton("Logout");

        logout.addActionListener(e->{
            frame.setContentPane(new Login(frame));
            frame.revalidate();
            frame.repaint();
        });

        headerRight.add(profile);
        headerRight.add(logout);
        header.add(title,BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelRight = new JPanel();
        JLabel labelCenter=new JLabel("Main dashboard content");
        JLabel labelRight = new JLabel("Right placeholder content");

        panelRight.add(labelRight);
//        panelCenter.add(labelCenter);
        panelCenter.add(new TimeTable(),BorderLayout.CENTER);

        frame.setSize(1280,720);
        panelCenter.setPreferredSize(new Dimension(900,720));
        panelRight.setPreferredSize(new Dimension(330,720));
        panelRight.setBackground(COLOR_PLUM);
        panelCenter.setBackground(Color.WHITE);

//        frame.add(new AdminMenu(frame), BorderLayout.WEST);
//        frame.add(panelRight, BorderLayout.EAST);
//        frame.add(panelCenter, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        add(new AdminMenu(frame), BorderLayout.WEST);
        add(panelRight, BorderLayout.EAST);
        add(panelCenter, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JButton styleButton(String text){
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
