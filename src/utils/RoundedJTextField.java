package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedJTextField extends JTextField{
    private int arcWidth = 15; // 圆角宽度
    private int arcHeight = 15; // 圆角高度
    private Color backgroundColor = new Color(255, 255, 255, 128); // 半透明白色背景
    private String hint; // 提示文字
    private boolean showingHint; // 是否正在显示提示文字

    public RoundedJTextField(String hint) {
        super(hint); // 初始化时显示提示文字
        this.hint = hint;
        this.showingHint = true;
        setOpaque(false); // 设置组件透明
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // 设置内边距
        setForeground(new Color(50, 50, 50, 128)); // 设置提示文字颜色

        // 添加焦点监听器
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    setText(""); // 清空提示文字
                    setForeground(Color.BLACK); // 输入时文字变为黑色
                    showingHint = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(hint); // 恢复提示文字
                    setForeground(new Color(50, 50, 50, 128)); // 恢复提示文字颜色
                    showingHint = true;
                }
            }
        });
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
        super.paintComponent(g); // 调用父类方法绘制文本
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
