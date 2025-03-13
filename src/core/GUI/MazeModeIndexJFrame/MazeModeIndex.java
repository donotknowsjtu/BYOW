package core.GUI.MazeModeIndexJFrame;

import core.GUI.MazeModeJFrame.MazeMode;
import utils.RoundedJButton;
import utils.RoundedJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 绘制迷宫模式的首页，监听输入的种子，迷宫的长和宽并调用世界生成UI工具生成世界
 */
public class MazeModeIndex {
    private JFrame mazeModeIndex;
    private JPanel mazeModeIndexCenterPanel;
    private JPanel escPanel;
    private long seed;
    private JButton newGame;
    private JButton history;
    private RoundedJTextField seedText;
    private RoundedJTextField LengthText;
    private RoundedJTextField WidthText;
    private RoundedJButton enter;
    private RoundedJButton ESC;
    private int Length;
    private int Width;
    private JFrame home;
    private GridBagConstraints gbc = new GridBagConstraints();

    public MazeModeIndex(JFrame home) {
        this.home = home;
        mazeModeIndex = new JFrame("迷宫模式首页");

        mazeModeIndex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeModeIndex.setSize(1000, 800);

        // 设置屏幕显示位置
        setLocation(mazeModeIndex);

        mazeModeIndexCenterPanel = new JPanel(new GridLayout(5, 1));
        mazeModeIndexCenterPanel.setLayout(new GridBagLayout());
        newGame = new JButton("new game");
        history = new JButton("history");
        // 设置布局
        setGBC1();

        mazeModeIndex.add(mazeModeIndexCenterPanel, BorderLayout.CENTER);
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

        // 处理中央面板
        // 移除 newGame 按钮
        mazeModeIndexCenterPanel.remove(newGame);
        mazeModeIndexCenterPanel.remove(history);

        // 创建新的 JTextField 和 JButton
        seedText = new RoundedJTextField("请输入你的种子");
        LengthText = new RoundedJTextField("请输入地图长度");
        WidthText = new RoundedJTextField("请输入地图宽度");

        // 设置为4行1列的网格布局，行间距为10
        seedText.setPreferredSize(new Dimension(300, 40));
        LengthText.setPreferredSize(new Dimension(300, 40));
        WidthText.setPreferredSize(new Dimension(300, 40));

        enter = new RoundedJButton("enter");

        // 设置gbc布局
        setGBC2();

        // 重新绘制 mazeModeIndexCenterPanel
        mazeModeIndexCenterPanel.revalidate();
        mazeModeIndexCenterPanel.repaint();


        // 添加左面板
        addWestPanel();


        // 监听 enter 按钮
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    seed = Long.parseLong(seedText.getText());
                    Length = Integer.parseInt(LengthText.getText());
                    Width = Integer.parseInt(WidthText.getText());
                    fireEnterClicked();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mazeModeIndex, "输入的种子或地图尺寸不是有效的整数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }

    // enter键被按下，生成逃离迷宫游戏界面
    private void fireEnterClicked() {
        new Thread(() -> {
            // 新建MazeMode界面
            MazeMode mazeMode = new MazeMode(seed, Length, Width);
            mazeMode.generate();
            // 延迟关闭 home frame
            try {
                Thread.sleep(500); // 延迟 500ms 关闭 home frame
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // 假装关闭 home frame（隐藏）
            mazeModeIndex.setVisible(false);
        }).start();
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

    /**
     * 设置布局为GridBagConstraints布局
     */
    private void setGBC2() {

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // 设置行间距

        gbc.gridx = 0;
        gbc.gridy = 0;
        mazeModeIndexCenterPanel.add(seedText, gbc);

        gbc.gridy = 1;
        mazeModeIndexCenterPanel.add(LengthText, gbc);

        gbc.gridy = 2;
        mazeModeIndexCenterPanel.add(WidthText, gbc);

        gbc.gridy = 3;
        mazeModeIndexCenterPanel.add(enter, gbc);
    }
    /**
     * 设置布局为GridBagConstraints布局
     */
    private void setGBC1() {

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // 设置行间距

        gbc.gridx = 0;
        gbc.gridy = 0;
        mazeModeIndexCenterPanel.add(newGame, gbc);

        gbc.gridy = 1;
        mazeModeIndexCenterPanel.add(history, gbc);
    }
    /**
     * 添加左面板
     */
    public void addWestPanel(){
        // 左面板
        // 返回按钮
        ESC = new RoundedJButton("ESC");
        ESC.setPreferredSize(new Dimension(200, 100));
        JPanel escPanel = new JPanel(new BorderLayout());
        escPanel.add(ESC, BorderLayout.NORTH);
        mazeModeIndex.add(escPanel, BorderLayout.WEST);

        // 监听esc按钮
        ESC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    // 中央面板移除组件
                    mazeModeIndexCenterPanel.remove(seedText);
                    mazeModeIndexCenterPanel.remove(LengthText);
                    mazeModeIndexCenterPanel.remove(WidthText);
                    mazeModeIndexCenterPanel.remove(enter);
                    // 重新展示原组件
                    setGBC1();
                    // 中央面板重新绘制
                    mazeModeIndexCenterPanel.revalidate();
                    mazeModeIndexCenterPanel.repaint();
                    // 移除esc面板
                    mazeModeIndex.remove(escPanel);

            }
        });
    }

}