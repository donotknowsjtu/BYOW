import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class RotateImageAroundPoint extends JPanel {
    private BufferedImage image;
    private Point rotationCenter; // 旋转中心点
    private Point imagePoint; // 图片上要与旋转中心重合的点
    private double angle = 0; // 旋转角度（弧度）

    public RotateImageAroundPoint(BufferedImage image, Point rotationCenter, Point imagePoint) {
        this.image = image;
        this.rotationCenter = rotationCenter;
        this.imagePoint = imagePoint;
    }

    public void setAngle(double angleInDegrees) {
        this.angle = Math.toRadians(angleInDegrees);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 保存原始变换
        AffineTransform originalTransform = g2d.getTransform();

        // 1. 平移画布，使旋转中心与图片上的点重合
        // 计算需要移动的距离：旋转中心坐标减去图片上点的坐标
        int dx = rotationCenter.x - imagePoint.x;
        int dy = rotationCenter.y - imagePoint.y;
        g2d.translate(dx, dy);

        // 2. 旋转画布
        // 旋转中心现在是(0,0)，因为我们已经平移了画布
        g2d.rotate(angle, imagePoint.x, imagePoint.y);

        // 3. 绘制图像
        g2d.drawImage(image, 0, 0, this);

        // 恢复原始变换
        g2d.setTransform(originalTransform);

        // 可选：绘制旋转中心点
        g2d.setColor(Color.RED);
        g2d.fillOval(rotationCenter.x - 3, rotationCenter.y - 3, 6, 6);
    }

    public static void main(String[] args) {
        // 示例用法
        JFrame frame = new JFrame("Image Rotation Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // 创建测试图像
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, 200, 100);
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(50, 25, 100, 50);
        g2d.dispose();

        // 旋转中心点（在窗口中的坐标）
        Point rotationCenter = new Point(400, 300);
        // 图片上要与旋转中心重合的点（图片坐标系中的点）
        Point imagePoint = new Point(100, 50); // 图片中心偏右下的点

        RotateImageAroundPoint panel = new RotateImageAroundPoint(image, rotationCenter, imagePoint);
        frame.add(panel);

        // 添加滑块控制旋转角度
        JSlider slider = new JSlider(0, 360, 0);
        slider.addChangeListener(e -> panel.setAngle(slider.getValue()));

        frame.add(slider, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}