package utils;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedJButton extends JButton {
    private int arcWidth = 15; // 圆角宽度
    private int arcHeight = 15; // 圆角高度
    private Color backgroundColor = new Color(255, 255, 255, 128); // 半透明白色背景

    public RoundedJButton(String text) {
        super(text);
        setOpaque(false); // 设置组件透明
        setContentAreaFilled(false); // 不填充内容区域
        setBorderPainted(false); // 不绘制默认边框
        setFocusPainted(false); // 不绘制焦点状态
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // 设置透明度

        // 绘制圆角矩形背景
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

        g2.dispose();
        super.paintComponent(g); // 调用父类方法绘制按钮文本
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 128)); // 半透明白色边框
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
        g2.dispose();
    }
}
