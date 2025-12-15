package edu.univ.erp.ui.auth;

import edu.univ.erp.access.LoginAccess;
import edu.univ.erp.auth.session.CurrentSession;
import edu.univ.erp.auth.store.AuthStore;
import edu.univ.erp.ui.admin.AdminDashboard;
import edu.univ.erp.ui.common.LoadingMsg;
import edu.univ.erp.ui.instructor.InstructorDashboard;
import edu.univ.erp.ui.student.Dashboard;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatLightLaf;
import org.jfree.chart.plot.dial.DialBackground;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Login extends JPanel{
    private JFrame frame;
    public Login(JFrame frame){
//        JFrame frame=new JFrame("University ERP - Login");
        this.frame=frame;
        JButton button = new JButton("Login");
        JLabel label_1=new JLabel("Enter your username: ");
        JLabel label_2=new JLabel("Enter your password: ");
        JTextField username=new JTextField();
        JPasswordField password=new JPasswordField();

        JPanel bgPanel=new BackgroundPanel("AP_Project_ERP/resources/background.png");
        bgPanel.setLayout(new MigLayout("center, center","",""));

        JPanel card=new JPanel(new MigLayout("wrap 1, gapy 10","[grow,fill]",""));
        card.setBackground(new Color(255,255,255,210));
        card.setBorder(BorderFactory.createEmptyBorder(25,40,25,40));
        card.putClientProperty("FlatLaf.styleClass","rounded");

        JLabel title=new JLabel("Login");
        title.setFont(title.getFont().deriveFont(Font.BOLD,22f));
        title.setHorizontalAlignment(SwingConstants.CENTER);

//        frame.setLayout(null);
//        JPanel panel=new JPanel(new MigLayout(
//                "wrap 2, inset 30",
//                "[right][grow,fill]",
//                "15[]"
//        ));
        frame.setSize(1024,1024);
//        label_1.setBounds(300,250,200,40);
//        username.setBounds(300,300,400,40);
//        label_2.setBounds(300,380,200,40);
//        password.setBounds(300,430,400,40);
//        button.setBounds(400,520,200,50);

        card.add(title, "gapbottom 10");
        card.add(label_1);
        card.add(username);
        card.add(label_2);
        card.add(password);
        card.add(new JLabel());
        card.add(button, "growx, gaptop 15");

        bgPanel.add(card,"w 600!");

//        frame.setContentPane(bgPanel);
        this.setLayout(new BorderLayout());
        this.add(bgPanel,BorderLayout.CENTER);

        frame.setBackground(Color.lightGray);
//        button.setForeground(Color.pink); //text color
        button.setBackground(Color.DARK_GRAY); //background color
        button.setForeground(Color.white);
        username.setBackground(Color.pink);
        password.setBackground(Color.pink);
//        label_1.setForeground(Color.white);
        label_1.setBackground(Color.DARK_GRAY);

        button.addActionListener(event->{
            String user= username.getText().trim();
            String pass=new String(password.getPassword());

            String hash = BCrypt.hashpw("abc", BCrypt.gensalt());
            System.out.println(hash);


            System.out.println("USERNAME ENTERED = [" + user + "]");
            System.out.println("USERNAME LENGTH = " + user.length());


            LoadingMsg loading=new LoadingMsg(frame,"Logging...");
            AuthStore authStore=new AuthStore();

            SwingWorker<Boolean, Void> worker=new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground(){
                    boolean isValid=authStore.validateUser(user,pass);
                    return isValid;
                }
                @Override
                protected void done(){
                    loading.hideDialog();
                    try{
                        boolean isValid=get();
                        if(isValid){
                            int id=authStore.getUserId(user);
                            String role=authStore.getUserRole(user);
                            CurrentSession.setUser(id,user,role);
                            authStore.updateLastLogin(id);

                            JOptionPane.showMessageDialog(frame,"Login Successful !");

                            if(LoginAccess.isStudent()){
//                            new Dashboard(frame);
                                frame.setContentPane(new Dashboard(frame));
                            }
                            else if(LoginAccess.isInstructor()){
//                            new InstructorDashboard(frame);
                                frame.setContentPane(new InstructorDashboard(frame));
                            }
                            else if(LoginAccess.isAdmin()){
//                            new AdminDashboard(frame);
                                frame.setContentPane(new AdminDashboard(frame));
                            }
                            frame.revalidate();
                            frame.repaint();
//                           frame.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(frame,"Invalid username or password !");
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            worker.execute();
            loading.showDialog();
        });

//        frame.setVisible(true);
    }

    static class BackgroundPanel extends  JPanel{
        private Image background;

        public BackgroundPanel(String imagePath){
            try{
                background= ImageIO.read(new java.io.File(imagePath));
            }
            catch(Exception e){
                System.out.println("Background image not found");
            }
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            if(background!=null){
                g.drawImage(background,0,0,getWidth(),getHeight(),this);
            }
        }
    }
}
