package edu.univ.erp;

import com.formdev.flatlaf.FlatLightLaf;
import edu.univ.erp.ui.auth.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(()->{
//            new Login().createLoginUI()
            JFrame frame=new JFrame("University ERP");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024,1024);
            frame.setContentPane(new Login(frame));
            frame.setVisible(true);
        });
    }

}
