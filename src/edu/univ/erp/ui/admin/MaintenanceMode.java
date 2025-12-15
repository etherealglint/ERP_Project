package edu.univ.erp.ui.admin;

import edu.univ.erp.service.MaintenanceService;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MaintenanceMode extends JPanel{
    private JFrame frame;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private MaintenanceService maintenanceService=new MaintenanceService();
    public MaintenanceMode(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(10,10,10,10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new AdminMenu(frame),BorderLayout.WEST);

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title=new JLabel("Maintenance Control");
        title.setFont(new Font("SansSerif",Font.BOLD,20));

        header.add(title,BorderLayout.WEST);
        add(header,BorderLayout.NORTH);

        JPanel container= new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.white);
//
//        JLabel title= new JLabel("Maintenance mode info");
//        panel1.add(new AdminMenu(frame), BorderLayout.CENTER);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);

//        JPanel topInfo=new JPanel();
//        topInfo.setLayout(new BorderLayout(10,10));
//        JLabel title=new JLabel("System Maintenance Control");
//        JButton home =new JButton("Home");

//        topInfo.add(home,BorderLayout.WEST);
//        topInfo.add(title,BorderLayout.CENTER);
//        add(topInfo,BorderLayout.NORTH);

//        JPanel panelCenter=new JPanel();
//        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
//        panelCenter.setBorder(new EmptyBorder(10,10,10,10));
//        JLabel label1=new JLabel("Maintainenace mode is Enabled.");

        boolean enabled=maintenanceService.isMaintenanceEnabled();
        String currentMessage=maintenanceService.getMaintenanceMessage();

        JLabel label1=new JLabel();
        label1.setForeground(COLOR_PLUM);
        label1.setFont(new Font("SansSerif",Font.BOLD,16));
        label1.setText("Maintenance mode is "+(enabled ? "Enabled" : "Disabled")+".");
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel msg1=new JLabel();
        msg1.setText(currentMessage);
        msg1.setForeground(Color.DARK_GRAY);
        msg1.setFont(new Font("SansSerif",Font.PLAIN,14));

        JLabel label2=new JLabel("Note: All users access, except for administrators, restricted in maintenance mode.");
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        label2.setForeground(Color.GRAY);
        label2.setFont(new Font("SansSerif",Font.ITALIC,13));

        JLabel msg=new JLabel("Enter the message to display(last message was as below):");
        msg.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField msgField=new JTextField(currentMessage,25);
        msgField.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        msgField.setAlignmentX(Component.LEFT_ALIGNMENT);
        msgField.setBackground(COLOR_VIOLET);
        msgField.setForeground(Color.WHITE);
        msgField.setCaretColor(Color.WHITE);
        msgField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        JToggleButton toggle=new JToggleButton(enabled ? "Disable Maintenance":"Enable Maintenance");
        toggle.setSelected(enabled);
        toggle.setBackground(COLOR_CORAL);
        toggle.setForeground(Color.WHITE);
        toggle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton save=new JButton("Save settings");
        JButton cancel=new JButton("Cancel");

        save.setBackground(COLOR_PLUM);
        save.setForeground(Color.white);
        cancel.setBackground(COLOR_VIOLET);
        cancel.setForeground(Color.WHITE);

//        panelCenter.setLayout(new BorderLayout(10,10));

//        JPanel panelContent=new JPanel();
//        panelContent.setLayout(new BorderLayout(10,10));

        container.add(label1);
        container.add(label2);
//        container.add(panelContent);
        container.add(msg);
        container.add(Box.createVerticalStrut(10));
        container.add(msg1);
        container.add(Box.createVerticalStrut(20));
        container.add(msgField);
        container.add(Box.createVerticalStrut(20));
        container.add(toggle);
        container.add(Box.createVerticalStrut(20));


        save.addActionListener(e->{
            String newMessage=msgField.getText();
            boolean newEnabled=toggle.isSelected();
            if (msgField.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"All Fields are required to be filled.");
                return;
            }
            maintenanceService.updateMaintenanceSettings(newEnabled, newMessage);
            label1.setText("Maintenance mode is currently: "+(newEnabled?"ENABLED":"DISABLED"));
            JOptionPane.showMessageDialog(this, "Maintenance settings updated! ","Success", JOptionPane.INFORMATION_MESSAGE);
        });

        cancel.addActionListener(e->{
            msg1.setText(currentMessage);
        });

//        home.addActionListener(e->{
//            frame.setContentPane(new AdminDashboard(frame));
//            frame.revalidate();
//            frame.repaint();
//        });

        toggle.addActionListener(e-> {
            toggle.setText(toggle.isSelected() ? "Disable Maintenance":"Enable Maintenance");
        });

        JPanel buttons=new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.add(save);
        buttons.add(cancel);
//        panelCenter.add(buttons,BorderLayout.SOUTH);
        container.add(buttons);
//        add(topInfo,BorderLayout.NORTH);
        add(container,BorderLayout.CENTER);

    }
}
