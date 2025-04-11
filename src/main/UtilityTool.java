package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaleImage(BufferedImage original, int length, int height){
        BufferedImage scaleImage = new BufferedImage(length, height, original.getType());
        Graphics2D g2 = scaleImage.createGraphics();
        g2.drawImage(original, 0, 0, length, height, null);
        g2.dispose();
        return scaleImage;
    }
}
