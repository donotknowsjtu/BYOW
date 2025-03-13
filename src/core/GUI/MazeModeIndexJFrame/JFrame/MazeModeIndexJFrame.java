package core.GUI.MazeModeIndexJFrame.JFrame;

import core.GUI.MazeModeIndexJFrame.JPanel.CenterPanel1;
import core.GUI.MazeModeIndexJFrame.JPanel.EscPanel1;
import core.GUI.MazeModeIndexJFrame.JPanel.EscPanel2;

import javax.swing.*;
import java.awt.*;

/**
 * 生成MazeModeIndex页面
 */

public class MazeModeIndexJFrame extends JFrame{


    public MazeModeIndexJFrame(){
        this.setTitle("迷宫模式首页");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        // 设置页面在屏幕中的显示位置
        setLocation(this);
        CenterPanel1 centerPanel1 = new CenterPanel1(this);
//        EscPanel1 escPanel1 = new EscPanel1(this);
    }


    /**
     * 将页面显示出来
     */
    public void setVisible(Boolean isTrue){
        this.setVisible(isTrue);

    }

    /**
     * 设置显示位置位于中央
     */
    private void setLocation(JFrame frame) {
        // 设置显示在屏幕中央
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // 获取窗口尺寸
        Dimension windowSize = frame.getSize();
        int windowWidth = windowSize.width;
        int windowHeight = windowSize.height;

        // 计算窗口居中位置
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;

        // 设置窗口位置
        frame.setLocation(x, y);
    }
}
