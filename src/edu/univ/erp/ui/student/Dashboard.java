package edu.univ.erp.ui.student;

import edu.univ.erp.access.LoginAccess;
import edu.univ.erp.ui.auth.Login;
import edu.univ.erp.ui.common.CourseCatalog;
import edu.univ.erp.ui.common.TimeTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Dashboard extends JPanel {
    private JFrame frame;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public Dashboard(JFrame frame) {

        if(!LoginAccess.isStudent()){
            JOptionPane.showMessageDialog(frame,"Access Denied");
            return;
        }
////        JFrame frame = new JFrame();
//        this.frame=frame;
//        setLayout(new BorderLayout());
////        JPanel panelLeft = new JPanel();
//        JPanel panelRight = new JPanel(new MigLayout("wrap 1, gapy15, inset 20","[grow,fill]",""));
//        panelRight.setPreferredSize(new Dimension(220,720));
//        panelRight.setBackground(COLOR_DARK);
//
//        JLabel title=new JLabel("Student Portal");
//        title.setFont(new Font("SansSerif",Font.BOLD,22));
//        title.setForeground(Color.WHITE);
//
//        JPanel panelCenter = new JPanel();
//        setLayout(new BorderLayout());
//
//
//        JLabel label_1 = new JLabel("Your Upcoming schedule is as follows.");
//        JLabel label_2 = new JLabel("Course Catalog");
//
//        frame.setSize(1280,720);
//        panelCenter.setPreferredSize(new Dimension(700,720));
//        panelRight.setPreferredSize(new Dimension(330,720));
//
//
//        panelCenter.setLayout(new BorderLayout());
//        panelCenter.add(label_2,BorderLayout.NORTH);
//        panelCenter.add(new TimeTable(frame));
//
//        panelRight.add(label_1);
//
//        add(panelRight, BorderLayout.EAST);
//        add(panelCenter, BorderLayout.CENTER);
        this.frame=frame;
        setLayout(new BorderLayout());
        JPanel sideBar= new JPanel(new MigLayout("wrap 1, gapy 15, insets 20","[grow,fill]",""));
        sideBar.setPreferredSize(new Dimension(220,720));
        sideBar.setBackground(COLOR_DARK);

        JLabel title=new JLabel("Student Portal");
        title.setFont(new Font("SansSerif",Font.BOLD,22));
        title.setForeground(Color.WHITE);

        JButton btnSchedule= createSideBarButton("Timetable");
//        JButton btnCatalog= createSideBarButton("Catalog");
        JButton btnProfile= createSideBarButton("Profile");
        JButton btnLogout= createSideBarButton("Logout");

        btnLogout.addActionListener(e->{
            frame.setContentPane(new Login(frame));
            frame.revalidate();
            frame.repaint();
        });

        sideBar.add(title,"gaptop 10");
        sideBar.add(btnSchedule);
//        sideBar.add(btnCatalog);
        sideBar.add(btnProfile);
//        sideBar.add(new StudentMenu(frame));
        sideBar.add(btnLogout,"push" );

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_PLUM);

        JLabel headerTitle=new JLabel("Welcome ");
        headerTitle.setFont(new Font("SansSerif", Font.BOLD,20));
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

        header.add(headerTitle,BorderLayout.WEST);

        JPanel content=new JPanel(new MigLayout("wrap 1,insets 20","[grow,fill]",""));
        content.setBackground(Color.WHITE);

        JPanel card=new JPanel(new MigLayout("wrap 1, gapy 15","[grow,fill]",""));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.styleClass","card");
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel cardtitle=new JLabel("Upcoming schedule");
        cardtitle.setFont(new Font("SansSerif",Font.BOLD,18));
        cardtitle.setForeground(COLOR_VIOLET);

        card.add(cardtitle,"gaptop 5");
        card.add(new TimeTable(frame),"grow");

        content.add(card, "grow");

        add(sideBar, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
    }
    private JButton createSideBarButton(String text){
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
