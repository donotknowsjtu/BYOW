package core.GUI.HomeJFrame;

import core.GUI.HomeJFrame.JPanel.CenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Home类创键游戏首页并监听玩家操作
 */
public class Home {
    private final String Home_page_name = "Home";
    private JFrame home = new JFrame(Home_page_name);
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

        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setSize(1000, 800);


        setLocation();
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

    private void setLocation(){
        // 设置显示在屏幕中央
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // 获取窗口尺寸
        Dimension windowSize = home.getSize();
        int windowWidth = windowSize.width;
        int windowHeight = windowSize.height;

        // 计算窗口居中位置
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;

        // 设置窗口位置
        home.setLocation(x, y);
    }
}
