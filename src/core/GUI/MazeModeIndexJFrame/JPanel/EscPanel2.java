package core.GUI.MazeModeIndexJFrame.JPanel;

import org.checkerframework.checker.units.qual.C;
import utils.RoundedJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscPanel2 extends JPanel {
    RoundedJButton ESC ;
    private JFrame frame;
    private CenterPanel2 centerPanel2;
    private CenterPanel1 centerPanel1;


    public EscPanel2(JFrame frame, CenterPanel2 centerPanel2, CenterPanel1 centerPanel1){
        this.frame = frame;
        this.centerPanel2 = centerPanel2;
        this.centerPanel1 = centerPanel1;
        ESC = new RoundedJButton("ESC");
        ESC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBacktoCenterPanel1();
            }
        });
        this.add(ESC, BorderLayout.NORTH);
        // 将面板添加到frame中
        frame.add(this, BorderLayout.WEST);
    }

    private void goBacktoCenterPanel1(){
        frame.remove(centerPanel2);
        frame.remove(this);
        frame.add(centerPanel1);
        frame.repaint();
    }
}
