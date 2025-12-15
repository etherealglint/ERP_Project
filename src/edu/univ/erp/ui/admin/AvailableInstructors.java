package edu.univ.erp.ui.admin;

import edu.univ.erp.domain.Section;
import edu.univ.erp.service.InstructorAllotmentService;
import edu.univ.erp.service.InstructorSectionService;
import edu.univ.erp.ui.instructor.InstructorMenu;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AvailableInstructors extends JPanel{
    private JFrame frame;
    private JLabel sectionId;
    private JLabel dayTime;
    private JLabel room;
    private JLabel capacity;
    private JLabel code;
    private JLabel semYear;
    private JTable instTable;
    private JButton assignInst;

    private Section selectedSection;
    private InstructorAllotmentService allotmentService=new InstructorAllotmentService();
    private InstructorSectionService instructorSectionService=new InstructorSectionService();

    public AvailableInstructors(Section section,JFrame frame){
        this.frame=frame;
        this.selectedSection=section;
        setLayout(new MigLayout("fill", "[300!][grow]","[]10[]"));
        setBorder(new EmptyBorder(10,10,10,10));

        add(new AdminMenu(frame));
        JPanel leftPanel=new JPanel(new MigLayout("wrap 2","[][grow]","[]10[]"));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Section and Instructor Details"));
        leftPanel.add(new JLabel("Section Id:"));
        sectionId=new JLabel();
        leftPanel.add(sectionId,"growx");
        leftPanel.add(new JLabel("Day/Time: "));
        dayTime=new JLabel();
        leftPanel.add(dayTime,"growx");
        leftPanel.add(new JLabel("Capacity: "));
        capacity=new JLabel();
        leftPanel.add(capacity,"growx");
        leftPanel.add(new JLabel("Semester"));
        semYear=new JLabel();
        leftPanel.add(semYear,"growx");
        leftPanel.add(new JLabel("Code: "));
        code=new JLabel();
        leftPanel.add(code,"growx");
        leftPanel.add(new JLabel("Room: "));
        room=new JLabel();
        leftPanel.add(room,"growx");

        instTable=new JTable(new DefaultTableModel(
                new Object[]{"Instructor ID","name"},0
        ));

        add(leftPanel,"growy");

        JPanel instructorPanel=new JPanel(new MigLayout("wrap 1","[grow,fill]","[]10[]"));
        instructorPanel.setBorder(BorderFactory.createTitledBorder("Instructor Details"));

//        instTable=new JTable();
        JScrollPane scroll=new JScrollPane(instTable);
        scroll.setPreferredSize(new Dimension(250,200));
        instructorPanel.add(scroll, "grow");

        assignInst=new JButton("Assign instructor");
        instructorPanel.add(assignInst,"align center");
        add(instructorPanel,"growy");

        loadSectionDetails();
        loadInstructorTable();

        assignInst.addActionListener(e->{
            int row=instTable.getSelectedRow();
            if (row<0){
                JOptionPane.showMessageDialog(this, "select an instructor");
                return;
            }
            int instructorId=(int) instTable.getValueAt(row,0);
            String msg=allotmentService.assignInstructor(instructorId,selectedSection.getSectionId());
            JOptionPane.showMessageDialog(this,msg);
        });

    }

    private void loadSectionDetails(){
        sectionId.setText(String.valueOf(selectedSection.getSectionId()));
        dayTime.setText(selectedSection.getDayTime());
        room.setText(selectedSection.getRoom());
        capacity.setText(String.valueOf(selectedSection.getCapacity()));
        semYear.setText(selectedSection.getSemester()+" / "+selectedSection.getYear());
        code.setText(selectedSection.getCourseCode());
    }

    private void loadInstructorTable(){
        String semester=selectedSection.getSemester();
        String courseCode = selectedSection.getCourseCode();
//        List<Section> sections=allotmentService.getAvailableSections(semester,courseCode);
        List<Integer> instId=allotmentService.getAvailableInstructors(selectedSection.getCourseId());
        DefaultTableModel model=(DefaultTableModel) instTable.getModel();
        model.setRowCount(0);
        for(Integer id: instId){
            model.addRow(new Object[]{
                    id,
                    "Instructor "+id
            });
        }
    }
}
