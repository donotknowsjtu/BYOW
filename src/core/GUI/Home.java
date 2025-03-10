package core.GUI;

import core.GUI.Home_component.CenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Home类创键游戏首页并监听玩家操作
 */
public class Home {
    private final String Home_page_name = "Home";
    /**
     * 构造函数
     * 传递参数：无需传参
     * 建议调用位置：Main主程序第一步
     */
    public Home(){

    }

    /**
     * 生成home页面
     */
    public void home_generate() {
        JFrame home = new JFrame(Home_page_name);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setSize(1000, 800);

        // 新建标题label
        JLabel title = new JLabel("BYOW");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setBorder(BorderFactory.createEmptyBorder(30, 50, 0, 50));
        home.add(title, BorderLayout.NORTH);

        // 新建中央面板, 三行一列
        CenterPanel centerPanel = new CenterPanel(home);
        home.add(centerPanel, BorderLayout.CENTER);

        home.setVisible(true);
    }

}
