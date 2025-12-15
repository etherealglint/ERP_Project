package edu.univ.erp.ui.common;

import javax.swing.*;
import java.awt.*;

public class LoadingMsg {
    private JDialog dialog;

    public LoadingMsg(JFrame frame,String msg){
        dialog=new JDialog(frame,"Please Wait...",true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel(msg, SwingConstants.CENTER),BorderLayout.CENTER);
        dialog.setLocationRelativeTo(frame);
        dialog.setSize(250,120);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
    public void showDialog(){
        SwingUtilities.invokeLater(()->{dialog.setVisible(true);});
    }
    public void hideDialog(){
        SwingUtilities.invokeLater(()->{dialog.setVisible(false);});
    }
}
