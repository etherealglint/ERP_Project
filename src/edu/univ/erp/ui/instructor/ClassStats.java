package edu.univ.erp.ui.instructor;

import edu.univ.erp.domain.Section;
import edu.univ.erp.service.ClassStatsService;
import edu.univ.erp.service.InstructorSectionService;
import edu.univ.erp.ui.student.StudentMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ClassStats extends JPanel{
    private JFrame frame;
//    private int sectionId;
    private JComboBox<Section> sectionJComboBox;
    private JPanel statsPanel;

    private static final Color COLOR_DARK=new Color(34,34,45);
    private static final Color COLOR_PLUM=new Color(65,46,63);
    private static final Color COLOR_VIOLET=new Color(115,66,86);
    private static final Color COLOR_DUSTYRED=new Color(157,79,82);
    private static final Color COLOR_CORAL=new Color(211,118,112);

    public ClassStats(JFrame frame, int instructorId){
        this.frame=frame;
        setLayout(new BorderLayout(10,10));
        setBackground(COLOR_DARK);
        setBorder(new EmptyBorder(10,10,10,10));

        JButton home=styleButton("home");
        home.addActionListener(e->{
            frame.setContentPane(new InstructorDashboard(frame));
            frame.revalidate();
            frame.repaint();
        });
//        this.sectionId=sectionId;
//        JPanel panel1= new JPanel();
//        JLabel title= new JLabel("Class Stats here");
//        panel1.add(new InstructorMenu(frame), BorderLayout.WEST);
//        panel1.add(title, BorderLayout.CENTER);
//        this.add(panel1);

        InstructorSectionService getSections=new InstructorSectionService();
        ClassStatsService statsService=new ClassStatsService();
//        int sectionId=1; // change it pls

        List<Section> sections=getSections.getMySections(instructorId);
        sectionJComboBox=new JComboBox<>(sections.toArray(new Section[0]));
        sectionJComboBox.setBackground(COLOR_VIOLET);
        sectionJComboBox.setForeground(Color.WHITE);
        sectionJComboBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        sectionJComboBox.addActionListener(e->{
            Section selected=(Section) sectionJComboBox.getSelectedItem();
            if (selected!=null){
                loadStatsForSection(selected.getSectionId());
            }
        });
        add(sectionJComboBox,BorderLayout.NORTH);
        add(home,BorderLayout.WEST);

        statsPanel=new JPanel(new BorderLayout());
        statsPanel.setBackground(COLOR_DARK);
        add(statsPanel,BorderLayout.CENTER);
        if(!sections.isEmpty()){
            loadStatsForSection(sections.get(0).getSectionId());
        }
    }

    public void loadStatsForSection(int sectionId){
        statsPanel.removeAll();
        statsPanel.setLayout(new BorderLayout());
        ClassStatsService statsService=new ClassStatsService();

        double avg=statsService.getAverageScore(sectionId);
        double max=statsService.getMaxScore(sectionId);
        double min=statsService.getMinScore(sectionId);
        int total=statsService.getTotalStudents(sectionId);
        Map<String,Integer> dist=statsService.getGradeDistribution(sectionId);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//        setBorder(new EmptyBorder(15,15,15,15));

        JPanel chartPanel=new JPanel(new GridLayout(2,1,10,10));
        chartPanel.setBackground(COLOR_DARK);
        //barchart
        DefaultCategoryDataset barDataset=new DefaultCategoryDataset();
//        barDataset.addValue(72,"Average","MTH-101");
//        barDataset.addValue(85,"Average","CSE-111");
        barDataset.addValue(avg,"Average","Section "+sectionId);

        JFreeChart barChart=ChartFactory.createBarChart(
                "Class Average Scores", "Section", "Score", barDataset
        );
        ChartPanel barChartPanel=new ChartPanel(barChart);
        barChart.setBackgroundPaint(COLOR_DARK);
        barChart.getTitle().setPaint(COLOR_CORAL);
        //piechart
        DefaultPieDataset pieDataset=new DefaultPieDataset();
        pieDataset.setValue("A(90-100)",dist.getOrDefault("A",0));
        pieDataset.setValue("B(90-70)", dist.getOrDefault("B",0));
        pieDataset.setValue("C(60-69)", dist.getOrDefault("C",0));
        pieDataset.setValue("D/F(<60)",dist.getOrDefault("F",0));

        JFreeChart pieChart=ChartFactory.createPieChart(
                "Score distribution", pieDataset, true, true, false
        );
        ChartPanel pieChartPanel=new ChartPanel(pieChart);
        pieChartPanel.setBackground(COLOR_DARK);
//        pieChartPanel.getTitle().setPaint(COLOR_CORAL);
        chartPanel.add(barChartPanel);
        chartPanel.add(pieChartPanel);

//        JPanel leftPanel=new JPanel();
//        JLabel avgLabel=new JLabel("Class Average score- <course name>");
//        JLabel scoreDist=new JLabel("Score Distribution- <course name>");

        JPanel rightPanel=new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.setBackground(COLOR_PLUM);
        rightPanel.setBorder(new EmptyBorder(20,20,20,20));
        rightPanel.setPreferredSize(new Dimension(300,0));

        JLabel summaryLabel=styledLabel("Summary");
        JLabel totalStudents=styledLabel("Total Students:"+total);
        JLabel maxScore=styledLabel("Highest Score"+max);
        JLabel minScore=styledLabel("Lowest Score"+min);

        JLabel gradeBreakdown=styledLabel("Grade Breakdown");
        JLabel aStudents=styledLabel("Number of students with A: "+dist.getOrDefault("A",0));
        JLabel bStudents=styledLabel("Number of Students with B: "+dist.getOrDefault("B",0));
        JLabel cStudents=styledLabel("Number of students with C: "+dist.getOrDefault("C",0));
        JLabel fStudents=styledLabel("Number of students with F: "+dist.getOrDefault("F",0));

        rightPanel.add(summaryLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(totalStudents);
//        rightPanel.add(new TextField("100"));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(maxScore);
//        rightPanel.add(new TextField("90"));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(minScore);
//        rightPanel.add(new TextField("60"));

        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(gradeBreakdown);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(aStudents);
        rightPanel.add(bStudents);
        rightPanel.add(cStudents);
        rightPanel.add(fStudents);

//        add(chartPanel, BorderLayout.CENTER);
//        add(rightPanel, BorderLayout.EAST);
        statsPanel.add(chartPanel, BorderLayout.CENTER);
        statsPanel.add(rightPanel, BorderLayout.EAST);

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private JLabel styledLabel(String txt){
        JLabel l=new JLabel(txt);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(14f));
        return l;
    }

    private JButton styleButton(String text){
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
