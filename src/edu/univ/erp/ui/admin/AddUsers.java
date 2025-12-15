package edu.univ.erp.ui.admin;

import edu.univ.erp.service.AddUserService;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddUsers extends JPanel{
    private JFrame frame;
    private JPanel rightPanel;
    private CardLayout cardLayout;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public AddUsers(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(20,20,20,20));
        setBackground(Color.WHITE);

        add(new AdminMenu(frame),BorderLayout.WEST);

//        JButton home=new JButton();
//        home.addActionListener(e->{
//            frame.setContentPane(new AdminDashboard(frame));
//            frame.revalidate();
//            frame.repaint();
//        });
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Adding Users info here");
//        panel1.add(new AdminMenu(frame), BorderLayout.CENTER);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);

        JPanel leftPanel=new JPanel();
        leftPanel.setPreferredSize(new Dimension(250,400));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(10,10,10,10));
        leftPanel.setBackground(COLOR_PLUM);

        JLabel title=new JLabel("Add User Details");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif",Font.BOLD,20));

        JTextField name=new JTextField(15);
        JTextField username=new JTextField(15);
        JTextField email=new JTextField(15);
        JTextField password=new JTextField(15);

        JLabel selectRole=new JLabel("SELECT ROLE");

        JRadioButton student=new JRadioButton("Student");
        JRadioButton instructor=new JRadioButton("Instructor");
        JRadioButton admin=new JRadioButton("Admin");
        JButton saveDetails=new JButton("SAVE USER");
        //grouping button to select only one at a time
        ButtonGroup group=new ButtonGroup();
        group.add(student);
        group.add(instructor);
        group.add(admin);

        student.setOpaque(false);
        student.setForeground(Color.WHITE);
        instructor.setOpaque(false);
        instructor.setForeground(Color.WHITE);
        admin.setOpaque(false);
        admin.setForeground(Color.WHITE);

        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createLabel("Name"));
        leftPanel.add(styleTextField(name));
        leftPanel.add(createLabel("Username:"));
        leftPanel.add(styleTextField(username));
        leftPanel.add(createLabel("Email"));
        leftPanel.add(styleTextField(email));
        leftPanel.add(createLabel("Password"));
        leftPanel.add(styleTextField(password));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(selectRole);
        leftPanel.add(student);
        leftPanel.add(instructor);
        leftPanel.add(admin);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(saveDetails);

//        add(leftPanel, BorderLayout.WEST);

        leftPanel.setBorder(new EmptyBorder(10,10,10,10));
        saveDetails.setBackground(COLOR_CORAL);
        saveDetails.setForeground(Color.WHITE);
        saveDetails.setFont(new Font("SansSerif",Font.BOLD,15));
        saveDetails.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

//        JPanel rightPanel=new JPanel();
//        JLabel rightTitle=new JLabel("<student/inst/admin> Information");
        cardLayout=new CardLayout();
        rightPanel=new JPanel(cardLayout);

        JPanel studentPanel=new JPanel();
        studentPanel.setLayout(new GridLayout(4,2,10,10));
        studentPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel studentTitle=new JLabel("Enter student information");
        JLabel rollNo=new JLabel("Enter Roll No.");
        JLabel program=new JLabel("Enter the program");
        JLabel year=new JLabel("Enter the year of enrollment");
//        JTextField sTitle=new JTextField();
        JTextField sID=new JTextField();
        JTextField sProgram=new JTextField();
        JTextField sYear=new JTextField();

        studentPanel.add(studentTitle);
        studentPanel.add(new JLabel(""));
        studentPanel.add(rollNo);
        studentPanel.add(styleTextField(sID));
        studentPanel.add((program));
        studentPanel.add(styleTextField(sProgram));
        studentPanel.add(year);
        studentPanel.add(styleTextField(sYear));
        studentPanel.setBackground(Color.WHITE);

        JPanel instructorPanel=new JPanel();
        instructorPanel.setLayout(new GridLayout(4,2,10,10));
        instructorPanel.setBorder(new EmptyBorder(10,10,10,10));
        JLabel instructorTitle=new JLabel("Enter instructor information");
//        JLabel userId=new JLabel("Enter user id");
        JLabel department=new JLabel("Enter the department");
        JLabel designation=new JLabel("Enter the designation");
//        JTextField instTitle=new JTextField();
//        JTextField instID=new JTextField();
        JTextField instdept=new JTextField();
        JTextField instDesg=new JTextField();

        instructorPanel.add(instructorTitle);
        instructorPanel.add(new JLabel(""));
//        instructorPanel.add(instTitle);
//        instructorPanel.add(userId);
//        instructorPanel.add(instID);
        instructorPanel.add(department);
        instructorPanel.add(styleTextField(instdept));
        instructorPanel.add(designation);
        instructorPanel.add(styleTextField(instDesg));
        instructorPanel.setBackground(Color.WHITE);

        rightPanel.add(studentPanel,"STUDENT");
        rightPanel.add(instructorPanel,"INSTRUCTOR");

//        add(rightPanel, BorderLayout.CENTER);

        student.addActionListener(e->{
            rightPanel.setVisible(true);
            cardLayout.show(rightPanel,"STUDENT");
        });
        instructor.addActionListener(e->{
            rightPanel.setVisible(true);
            cardLayout.show(rightPanel,"INSTRUCTOR");
        });
        admin.addActionListener(e->{
            rightPanel.setVisible(false);
        });

        AddUserService service=new AddUserService();

        saveDetails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveDetails.setBackground(COLOR_VIOLET);
            }
            public void mouseExited(MouseEvent e) {
                saveDetails.setBackground(COLOR_CORAL);
            }
        });

        saveDetails.addActionListener(e->{
            String fullName=name.getText();
            String uname=username.getText();
            String mail=email.getText();
            String pass=password.getText();

            String msg="";

            if(student.isSelected()){
                String roll=sID.getText();
                String prog = sProgram.getText();
                int yearVal=Integer.parseInt(sYear.getText());

                msg=service.addStudentUser(uname,pass, roll, prog, yearVal);
            }
            if(instructor.isSelected()){
                String dept=instdept.getText();
                String desg=instDesg.getText();
                msg = service.addInstructorUser(uname,pass,dept,desg);
            }
            //fix admin
            if(admin.isSelected()){
                msg=service.addAdminUser(uname,pass);
            }
            JOptionPane.showMessageDialog(this,msg);
        });

        selectRole.setForeground(Color.WHITE);

        JPanel container=new JPanel(new GridBagLayout());
        container.setBackground(new Color(245,245,245));
        add(container, BorderLayout.CENTER);

        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,20,10,20);
        gbc.gridx=0;
        container.add(leftPanel,gbc);
        gbc.gridx=1;
        container.add(rightPanel,gbc);

        student.setSelected(true);
        rightPanel.setVisible(true);
        cardLayout.show(rightPanel,"STUDENT");

        setVisible(true);
    }

    private JLabel createLabel(String text){
        JLabel l=new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }
    private JTextField styleTextField(JTextField f){
//        JTextField f=new JTextField(text);
        f.setBackground(COLOR_VIOLET);
        f.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        f.setFont(new Font("SansSerif",Font.PLAIN,14));
        f.setForeground(Color.WHITE);
        return f;
    }

}
