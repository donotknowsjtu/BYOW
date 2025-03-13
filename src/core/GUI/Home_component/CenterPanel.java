package core.GUI.Home_component;

import core.GUI.Mode1.MazeModeIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 中央面板类
public class CenterPanel extends JPanel {

    /**
     * 引用home frame，便于对home frame进行操作
     */
    public JFrame home;


    public CenterPanel(JFrame home){
        this.home = home;
        panel();
    }
    private void panel(){
        // 设置布局为三行一列
        this.setLayout(new GridLayout(3, 1));
        // 在面板中新建新建mode label
        JLabel mode_label = new JLabel("choose your mode as follows");
        mode_label.setHorizontalAlignment(JLabel.CENTER);
        mode_label.setFont(new Font(mode_label.getFont().getName(), mode_label.getFont().getStyle(), 30));
        this.add(mode_label);

        JButton button1 = new JButton("maze mode");
        button1.setFont(new Font(button1.getFont().getName(), button1.getFont().getStyle(), 30));
        this.add(button1);
        // button1添加事件监听器监听点击事件
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 触发按钮点击事件，进入MazeMode首页
                fireButton1Clicked();
            }
        });

        JButton button2 = new JButton("mode to be launched");
        button2.setFont(new Font(button2.getFont().getName(), button2.getFont().getStyle(), 30));
        this.add(button2);
    }



    // mode1被选择，执行@MazeMode类的方法，刷新MazeMode的首页
    private void fireButton1Clicked(){
        new Thread(() -> {
                // 新建MazeMode首页
                MazeModeIndex mazeModeIndex = new MazeModeIndex(home);
                mazeModeIndex.generate();

                // 延迟关闭 home frame
                try {
                    Thread.sleep(500); // 延迟 500ms 关闭 home frame
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                 // 假装关闭 home frame（隐藏）
                home.setVisible(false);
        }).start();

    }




}

