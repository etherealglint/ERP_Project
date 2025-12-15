package edu.univ.erp.ui.student;

import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.data.SectionTable;
import edu.univ.erp.domain.Section;
import edu.univ.erp.service.CourseRegestrationService;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class CourseRegistration extends JPanel{
    private JFrame frame;
    private JTable table;
    private JTable finalCourseTable;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private CardLayout cardLayout;
    private Set<String> addedCourses = new HashSet<>();
    private JPanel tablePanel; // to store the two different tables

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    private void loadSavedCourses(){
        model2.setRowCount(0);
        CourseRegestrationService service=new CourseRegestrationService();
        int studentId=CurrentSession.getUserId();
//        List<Section> catalog=service.getCatalog();
        List<Section> saved=service.getSavedCourses(studentId);
        for(Section s:saved){
            model2.addRow(new Object[]{
                    s.getSectionId(),
                    s.getCourseId(),
                    s.getCourseTitle(),
                    s.getCapacity(),
                    s.getInstructorId(),
                    s.getCredits()
            });
        }
    }


    public CourseRegistration(JFrame frame){
//        this.frame=frame;
//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Register Courses here");
//        panel1.add(new StudentMenu(frame), BorderLayout.WEST);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);

        this.frame=frame;
        JLabel title= new JLabel("Register for courses here.");
        title.setFont(new Font("SansSerif",Font.BOLD,20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

        JPanel header=new JPanel(new BorderLayout());
        header.setBackground(COLOR_PLUM);
        header.setPreferredSize(new Dimension(800,60));

        frame.setTitle("Registration");
        setSize(800,600);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        StudentMenu menu=new StudentMenu(frame);
        menu.setPreferredSize(new Dimension(250,0));
        add(menu, BorderLayout.WEST);
        header.add(title,BorderLayout.WEST);
        add(header,BorderLayout.NORTH);

//        add(title, BorderLayout.NORTH);

        String[] columns={"Section Id","Code","Title","Capacity","Instructor","Credits"};
//        Object[][] data={
//                {"CSE101","IP",300,"ABC"},
//                {"MTH101","LA",300,"ABC"}
//        };
        model = new DefaultTableModel(columns,0){
            //for table sorting add here after overriding
        };
        CourseRegestrationService service=new CourseRegestrationService();
        List<Section> catalog=service.getCatalog();
        for(Section s:catalog){
            model.addRow(new Object[]{
                    s.getSectionId(),
                    s.getCourseId(),
                    s.getCourseTitle(),
                    s.getCapacity(),
                    s.getInstructorId(),
                    s.getCredits()
            });
        }

        table=new JTable(model);
        styleTable(table);

        String[] col2={"SectionId","Code","Title","Capacity","Instructor","Credits"};
        Object[][] data2={};
        model2=new DefaultTableModel(data2,col2);
        finalCourseTable=new JTable(model2);
        styleTable(finalCourseTable);

        cardLayout=new CardLayout();
        tablePanel=new JPanel(cardLayout);
        tablePanel.add(new JScrollPane(table),"table");
        tablePanel.add(new JScrollPane(finalCourseTable),"finalCourseTable");

        cardLayout.show(tablePanel, "table"); //initially show this table

        JPanel controlPanel=new JPanel();
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));
        //controlPanel needs buttons
        JButton addCourse = styledButton("Add");
        JButton dropCourse = styledButton("Drop");
        JButton printPage = styledButton("PrintPage");
        JButton showSelected=styledButton("Saved courses");
        JButton showCatalog=styledButton("Show course catalog");
        JButton exportTranscript=styledButton("Transcript");
        controlPanel.add(showCatalog);
        controlPanel.add(addCourse);
        controlPanel.add(dropCourse);
        controlPanel.add(printPage);
        controlPanel.add(showSelected);
        controlPanel.add(exportTranscript);


        addCourse.addActionListener(e->{
            int row=table.getSelectedRow();
            if(row==-1){
                return;
            }
            int modelRow=table.convertRowIndexToModel(row);
            int sectionId=(Integer)model.getValueAt(modelRow,0);
            int studentId= CurrentSession.getUserId();
//            CourseRegestrationService service=new CourseRegestrationService();
            String result=service.register(studentId,sectionId);
            JOptionPane.showMessageDialog(this,result);
            if (result.equals("Regestration sucessful.")){
                model2.addRow(new Object[]{
                        model.getValueAt(modelRow,0),
                        model.getValueAt(modelRow,1),
                        model.getValueAt(modelRow,2),
                        model.getValueAt(modelRow,3),
                        model.getValueAt(modelRow,4),
                        model.getValueAt(modelRow,5)
                });
            }
        });
//        addCourse.addActionListener(e->{
//            int selectedRow=table.getSelectedRow();
//            if(selectedRow!=-1){
//                int modelRow=table.convertRowIndexToModel(selectedRow);
////                model2.addRow(new Object[][data[][modelRow]]);
//                Object courseCode=model.getValueAt(modelRow,0);
////                boolean copy=false;
////                for(int i=0; i<model2.getRowCount();i++){
////                    if(model2.getValueAt(i,0).equals(courseCode)){
////                        copy=true;
////                        break;
////                    }
////                }
//                if (addedCourses.contains(courseCode)) {
//                    JOptionPane.showMessageDialog(this, "Course " + courseCode + " is already selected.", "Duplicate Course entry", JOptionPane.WARNING_MESSAGE);
//                } else {
//                    int columnCount = model.getColumnCount();
//                    Object[] rowData= new Object[columnCount];
//                    for(int i=0; i<columnCount; i++){
//                        rowData[i]=model.getValueAt(modelRow, i);
//                    }
//                    model2.addRow(rowData);
//                    addedCourses.add(courseCode.toString());
//                }
//            }
//        });
//        dropCourse.addActionListener(e->{
//            int selectedRow=finalCourseTable.getSelectedRow();
//            if(selectedRow != -1){
//                int modelRow=finalCourseTable.convertRowIndexToModel(selectedRow);
//                Object courseCode=model.getValueAt(modelRow,0);
//                model2.removeRow(modelRow);
//                addedCourses.remove(courseCode);
//            }
//        });
        dropCourse.addActionListener(e->{
            int row=finalCourseTable.getSelectedRow();
            if(row==-1){
                return;
            }
            int modelRow=finalCourseTable.convertRowIndexToModel(row);
            int sectionId=(Integer)model2.getValueAt(modelRow,0);
            int studentId=CurrentSession.getUserId();
//            CourseRegestrationService service=new CourseRegestrationService();
            String result=service.drop(studentId,sectionId);
            JOptionPane.showMessageDialog(this,result);
            loadSavedCourses();
        });
        showSelected.addActionListener(e->{
            cardLayout.show(tablePanel,"finalCourseTable");
        });
        showCatalog.addActionListener(e->{
            cardLayout.show(tablePanel,"table");
        });

        printPage.addActionListener(e->{
            try{
                finalCourseTable.print();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        exportTranscript.addActionListener(e->{
            try{
                exportTranscriptPDF();
            }
            catch(Exception e1){
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error generating PDF.");
            }
        });

        table.getSelectionModel().addListSelectionListener(event->{
            int viewRow=table.getSelectedRow();
            if(viewRow >=0){
                int modelRow=table.convertRowIndexToModel(viewRow);
                System.out.println("Selected: "+model.getValueAt(modelRow,0));
            }
        });

        this.add(controlPanel, BorderLayout.NORTH);

//        table.setRowHeight(28);
//        table.setGridColor(Color.LIGHT_GRAY);
//        //can add a selection mode for students
//        table.setFillsViewportHeight(true);

        this.add(tablePanel,BorderLayout.CENTER);
        loadSavedCourses();
//        this.add(new JScrollPane(table),BorderLayout.CENTER);

        frame.setContentPane(this);
        frame.setVisible(true);
    }

    private void styleTable(JTable t){
        t.setRowHeight(28);
        t.setGridColor(Color.LIGHT_GRAY);
        t.setFillsViewportHeight(true);
        t.setSelectionBackground(COLOR_CORAL);
        t.setSelectionForeground(Color.WHITE);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD,14));
        t.getTableHeader().setBackground(COLOR_DUSTYRED);
        t.getTableHeader().setForeground(Color.WHITE);
    }

    private JButton styledButton(String text){
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

    private void exportTranscriptPDF() throws Exception{
        JFileChooser ch=new JFileChooser();
        ch.setSelectedFile(new File("Transcript.pdf"));

        if(ch.showSaveDialog(this) != JFileChooser.APPROVE_OPTION){
            return;
        }
        String path=ch.getSelectedFile().getAbsolutePath();
        try(org.apache.pdfbox.pdmodel.PDDocument doc=new org.apache.pdfbox.pdmodel.PDDocument()){
            PDPage page=new PDPage();
            doc.addPage(page);
            PDPageContentStream c=new PDPageContentStream(doc,page);

            PDType1Font font=new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            c.setFont(font,12);
            c.beginText();
            c.showText("Student Transcript");
            c.endText();
            float y=720;
            c.beginText();
            c.newLineAtOffset(50,y);
            c.showText("Section Id  Code  Title            Credits ");
            c.endText();
            y-=20;

            for(int i=0;i<model2.getRowCount(); i++){
                if(y<50){
                    break;
                }
                String line=model2.getValueAt(i,0)+"          "+model2.getValueAt(i,1)+"      "+model2.getValueAt(i,2)+"         "+model2.getValueAt(i,5);
                c.beginText();
                c.newLineAtOffset(50,y);
                c.showText(line);
                c.endText();
                y-=20;
            }
            c.close();
            doc.save(path);
        }
        JOptionPane.showMessageDialog(this,"Transcript saved as PDF.");

    }}
