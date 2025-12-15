package edu.univ.erp.ui.admin;

import edu.univ.erp.domain.Section;
import edu.univ.erp.service.InstructorAllotmentService;
import edu.univ.erp.service.InstructorSectionService;
import edu.univ.erp.auth.session.CurrentSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InstructorAllotment extends JPanel {
    private JFrame frame;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private List<Section> availableSections;
    public InstructorAllotment(JFrame frame){
        this.frame=frame;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(15,15,15,15));

        add(new AdminMenu(frame), BorderLayout.WEST);

//        JButton home=new JButton("Home");
//        home.addActionListener(e->{
//            frame.setContentPane(new AdminDashboard(frame));
//            frame.revalidate();
//            frame.repaint();
//        });

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title=new JLabel("Instructor Allotment");
        title.setFont(new Font("SansSerif",Font.BOLD,20));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        //using cardlayout have panel1 in card 1
        JPanel panel1=new JPanel();
        panel1.setBackground(Color.white);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(new EmptyBorder(10,10,10,10));

//        home.setAlignmentX(Component.LEFT_ALIGNMENT);
//        panel1.add(home);

//        JLabel title=new JLabel("INSTRUCTOR SECTION TO ALLOT");
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//        panel1.add(title);

        JLabel page1Title=new JLabel("Select section to allot");
        panel1.add(page1Title);
        panel1.add(new JLabel(""));

        JPanel semRow=new JPanel();
        semRow.setLayout(new BoxLayout(semRow,BoxLayout.X_AXIS));
        semRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel semAllot=new JLabel("Semester:");
        semAllot.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField sem=new JTextField();
        sem.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        semRow.add(semAllot);
        semRow.add(Box.createHorizontalStrut(10));
        semRow.add(sem);

        panel1.add(semRow);
        panel1.add(Box.createHorizontalStrut(10));

        JPanel courseRow=new JPanel();
        courseRow.setLayout(new BoxLayout(courseRow,BoxLayout.X_AXIS));
        courseRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel courseAllot=new JLabel("Course Code:");
        courseAllot.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField course=new JTextField();
        course.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        courseRow.add(courseAllot);
        courseRow.add(Box.createHorizontalStrut(10));
        courseRow.add(course);

        panel1.add(courseRow);
        panel1.add(Box.createHorizontalStrut(10));
//

        JLabel availableSec=new JLabel("Available Sections");
        availableSec.setAlignmentX(Component.LEFT_ALIGNMENT);
        availableSec.setBorder(new EmptyBorder(10,0,5,0));
        panel1.add(availableSec);
        panel1.add(Box.createVerticalStrut(10));

        String[] col={"Section", "Info"};
//        Object[][] data={
//                {"Section A","MWF 10-11:50 AM | Room No. B003"},
//                {"Section C", "MWT 12:00-1:30 | Room No. B003"}
//        };
//        JTable sectionTable=new JTable(new DefaultTableModel(data,col));

        DefaultTableModel model=new DefaultTableModel(col,0);
        JTable sectionTable=new JTable(model);

        JScrollPane scroll=new JScrollPane(sectionTable);
        scroll.setPreferredSize(new Dimension(500,150));
        panel1.add(scroll);
        panel1.add(Box.createVerticalStrut(10));

        JPanel selectRow=new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectRow.add(new JLabel("Select Section: "));
//        JComboBox<String> selectBox=new JComboBox<>(new String[]{
//                "Section A","Section B"
//        });

        JComboBox<String> selectBox=new JComboBox<>();

        selectBox.setPreferredSize(new Dimension(200,25));
        selectRow.add(selectBox);
        panel1.add(selectRow);
        panel1.add(Box.createVerticalStrut(10));

        JPanel controlPanel=new JPanel();
        JButton prev=new JButton("Previous");
        JButton cancel=new JButton("Cancel");
        JButton next=new JButton("Next");
        controlPanel.add(prev);
        controlPanel.add(cancel);
        controlPanel.add(next);

        panel1.add(controlPanel);

        InstructorAllotmentService service=new InstructorAllotmentService();
        ActionListener fetch=e->{
            String semVal=sem.getText();
            String courseVal=course.getText();

            List<Section> list=service.getAvailableSections(semVal,courseVal);
            DefaultTableModel m=(DefaultTableModel) sectionTable.getModel();
            m.setRowCount(0);
            for(Section s:list){
                m.addRow(new Object[]{
                        s.getSectionId(),
                        "Course "+s.getCourseId()+" | "+s.getDayTime()+" | "+s.getRoom()
                });
            }
            selectBox.removeAllItems();
            for(Section s: list){
                selectBox.addItem("Section "+s.getSectionId());
            }
            availableSections=list;
        };

        sem.addActionListener(fetch);
        course.addActionListener(fetch);

        next.addActionListener(e->{
            int row =sectionTable.getSelectedRow();
            if(row<0){
                JOptionPane.showMessageDialog(this,"Please select a section. ");
                return;
            }
            Section selected=availableSections.get(row);
            frame.setContentPane(new AvailableInstructors(selected,frame));
            frame.revalidate();
            frame.repaint();
//            if(availableSections==null || availableSections.isEmpty()){
//                JOptionPane.showMessageDialog(this,"No section available");
//                return;
//            }
//            int index=selectBox.getSelectedIndex();
//            if (index < 0) {
//                JOptionPane.showMessageDialog(this,"Please select a section");
//                return;
//            }
//            Section chosen=availableSections.get(index);
//            InstructorAllotmentService service2=new InstructorAllotmentService();
//            int instructorId=CurrentSession.getUserId();
//            String msg=service2.assignInstructor(instructorId, chosen.getSectionId());
//            JOptionPane.showMessageDialog(this,msg);
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel1,BorderLayout.CENTER);
    }
}
