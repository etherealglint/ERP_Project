package edu.univ.erp.ui.instructor;

import edu.univ.erp.access.LoginAccess;
import edu.univ.erp.ui.common.TimeTable;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JPanel {
    private JFrame frame;
    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);


    public InstructorDashboard(JFrame frame) {

        if(!LoginAccess.isInstructor()){
            JOptionPane.showMessageDialog(frame,"Access Denied");
            return;
        }
//        JFrame frame = new JFrame();
        this.frame=frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_PLUM);

        JLabel title=new JLabel("Instructor Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif",Font.BOLD,22));
        title.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));
        header.add(title,BorderLayout.WEST);
        add(header,BorderLayout.NORTH);

//        JPanel panelRight = new JPanel();
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setBorder(BorderFactory.createEmptyBorder(30,40,30,30));

        JLabel label1 = new JLabel("Welcome ");
        label1.setFont(new Font("SansSerif",Font.BOLD,20));
        label1.setForeground(COLOR_DARK);
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label2 = new JLabel("Timetable");
        label2.setFont(new Font("SansSerif",Font.PLAIN,16));
        label2.setForeground(Color.DARK_GRAY);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCenter.add(label1);
        panelCenter.add(label2);
        panelCenter.add(new TimeTable());

        frame.setSize(1280,720);
        panelCenter.setPreferredSize(new Dimension(700,720));
//        panelRight.setPreferredSize(new Dimension(330,720));

//        panelRight.add(label2);
//        panelCenter.add(label1);
//        frame.add(new InstructorMenu(frame), BorderLayout.WEST );
//        frame.add(panelRight, BorderLayout.EAST);
//        frame.add(panelCenter, BorderLayout.CENTER);

        add(new InstructorMenu(frame),BorderLayout.WEST);
//        add(panelRight, BorderLayout.EAST);
        add(panelCenter, BorderLayout.CENTER);

//        frame.setVisible(true);
    }
}
