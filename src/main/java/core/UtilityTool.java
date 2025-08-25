package core;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component("utilityTool")
public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage original, int length, int height){
        BufferedImage scaleImage = new BufferedImage(length, height, original.getType());
        Graphics2D g2 = scaleImage.createGraphics();
        g2.drawImage(original, 0, 0, length, height, null);
        g2.dispose();
        return scaleImage;
    }

    /**
     * 计算点p1到点p2的连线与水平向右方向的夹角（弧度）
     * @param p1 固定点
     * @param p2 鼠标点
     * @return 角度（弧度），范围在 -π 到 π 之间
     */
    public double calculateAngle(Point p1, Point p2) {
        // 计算两点之间的差值
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;

        // 使用Math.atan2计算角度（结果在 -π 到 π 之间）
        return Math.atan2(dy, dx);
    }
}
