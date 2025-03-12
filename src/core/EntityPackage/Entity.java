package core.EntityPackage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x , y;
    public int speed = 4;

    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public String direction;
    public int CharacterCount = 0;
    public int CharacterStep = 1;

    public Rectangle  CollisionRect;
    public boolean isCollision = false;



}

