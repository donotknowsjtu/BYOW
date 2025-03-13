package core.GUI.MazeModeIndexJFrame.JPanel;

import utils.RoundedJButton;
import utils.RoundedJTextField;

import javax.swing.*;
import java.awt.*;

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
            frame.add(this, BorderLayout.CENTER);
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
}
