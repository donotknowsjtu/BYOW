package core.GUI.Mode1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 绘制迷宫模式的首页，监听输入的种子，迷宫的长和宽并调用世界生成UI工具生成世界
 */
public class MazeModeIndex {
    private JFrame mazeModeIndex;
    private JPanel panel;
    private long seed;
    private JButton newGame;
    private JButton history;
    private JTextField seedText;
    private JTextField LengthText;
    private JTextField WidthText;
    private JButton enter;
    private int Length;
    private int Width;

    public MazeModeIndex() {
        mazeModeIndex = new JFrame("迷宫模式首页");

        mazeModeIndex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeModeIndex.setSize(1000, 800);

        // 设置屏幕显示位置
        setLocation();

        panel = new JPanel(new GridLayout(5, 1));
        newGame = new JButton("new game");
        history = new JButton("history");
        panel.add(newGame);
        panel.add(history);

        mazeModeIndex.add(panel, BorderLayout.CENTER);
    }

    public void generate() {
        // 监听 newGame 按钮
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialize();

            }
        });

        mazeModeIndex.setVisible(true);
    }

    /**
     * 生成文本框并获取 seed 和地图尺寸
     */
    private void initialize() {
        // 移除 newGame 按钮
        panel.remove(newGame);
        panel.remove(history);

        // 创建新的 JTextField 和 JButton
        seedText = new JTextField("please enter your seed.");
        LengthText = new JTextField("please enter your wanted maze's length, which must be an Integer");
        WidthText = new JTextField("please enter your wanted maze's width, which must be an Integer");
        enter = new JButton("enter");

        // 将新的组件添加到 panel
        panel.add(seedText);
        panel.add(LengthText);
        panel.add(WidthText);
        panel.add(enter);

        // 监听 enter 按钮
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    seed = Long.parseLong(seedText.getText());
                    Length = Integer.parseInt(LengthText.getText());
                    Width = Integer.parseInt(WidthText.getText());
                    MazeMode mazeMode = new MazeMode(seed, Length, Width);
                    mazeMode.generate();
                } catch (NumberFormatException ex) {
                    System.out.println("输入的种子或长度或宽度不是有效的整数！");
                }
            }
        });

        // 重新绘制 panel
        panel.revalidate();
        panel.repaint();
    }

    /**
     * 设置显示位置位于中央
     */
    private void setLocation(){
        // 设置显示在屏幕中央
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // 获取窗口尺寸
        Dimension windowSize = mazeModeIndex.getSize();
        int windowWidth = windowSize.width;
        int windowHeight = windowSize.height;

        // 计算窗口居中位置
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;

        // 设置窗口位置
        mazeModeIndex.setLocation(x, y);
    }
}