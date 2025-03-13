package core.GUI.MazeModeIndexJFrame.JPanel;

import core.GUI.MazeModeJFrame.MazeMode;
import utils.RoundedJButton;
import utils.RoundedJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 中心面板二，由中心面板一自动调用生成，接收用户传入参数并启动游戏模式一
 */
public class CenterPanel2 extends JPanel{
    private JFrame frame;
    private CenterPanel1 centerPanel1;
    private RoundedJTextField seedText;
    private RoundedJTextField LengthText;
    private RoundedJTextField WidthText;
    private RoundedJButton enter;
    private GridBagConstraints gbc = new GridBagConstraints();

    public CenterPanel2(JFrame frame, CenterPanel1 centerPanel1){
        this.frame = frame;
        this.centerPanel1 = centerPanel1;
        // 创建新的 JTextField 和 JButton
        seedText = new RoundedJTextField("请输入你的种子");
        LengthText = new RoundedJTextField("请输入地图长度");
        WidthText = new RoundedJTextField("请输入地图宽度");

        // 设置为4行1列的网格布局，行间距为10
        seedText.setPreferredSize(new Dimension(300, 40));
        LengthText.setPreferredSize(new Dimension(300, 40));
        WidthText.setPreferredSize(new Dimension(300, 40));

        enter = new RoundedJButton("enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 生成MazeMode页面
                mazeModeJFrameGenerate();
            }

        });
        // 设置gbc布局, 将文本框和按钮添加到CenterPanel2中
        setGBC();
    }

    /**
     * 将CenterPanel1隐藏，CenterPanel2显示出来，刷新frame
     * @param isTrue
     */
    public void setVisible(Boolean isTrue){
        if(isTrue){
            frame.remove(centerPanel1);
            EscPanel2 escPanel2 = new EscPanel2(frame, this, centerPanel1);
            frame.add(this, BorderLayout.CENTER);
            frame.add(escPanel2, BorderLayout.WEST);
            frame.repaint();
        }
    }
    /**
     * 设置布局为GridBagConstraints布局并添加组件
     */
    private void setGBC() {

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // 设置行间距

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(seedText, gbc);

        gbc.gridy = 1;
        this.add(LengthText, gbc);

        gbc.gridy = 2;
        this.add(WidthText, gbc);

        gbc.gridy = 3;
        this.add(enter, gbc);
    }

    /**
     * 生成mazeMode页面
     */
    public void mazeModeJFrameGenerate(){
        try {
            Long seed = Long.parseLong(seedText.getText());
            int Length = Integer.parseInt(LengthText.getText());
            int Width = Integer.parseInt(WidthText.getText());
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
                // 假装关闭 MazeModeIndex frame（隐藏）
                this.setVisible(false);
            }).start();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "输入的种子或地图尺寸不是有效的整数！", "输入错误", JOptionPane.ERROR_MESSAGE);
        }


    }

}
