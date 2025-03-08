package core;

import edu.princeton.cs.algs4.StdDraw;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{


    public Player (double x, double y)
    {
        this.x = x;
        this.y = y;

        direction = "up";

        getPlayerImage();
    }




    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_up2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_up3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_up4.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_down2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_down3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_down4.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_left3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_left4.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_right3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/_right4.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        double newX = x;
        double newY = y;

        if(StdDraw.isKeyPressed(KeyEvent.VK_W)){
            direction = "up";
            newY += speed;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
            direction = "right";
            newX += speed;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_X)) {
            direction = "down";
            newY -= speed;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_A)){
            direction = "left";
            newX -= speed;
        }
        int tileX = (int) newX;
        int tileY = (int) newY;

        if(WorldGeneration.isTileWalkable(tileX,tileY)){
            x = newX;
            y = newY;
        }

        CharacterCount++;
        if(CharacterCount > 12){
            if (CharacterStep < 4){
                CharacterStep++;
            }else{
                CharacterStep = 1;
            }
            CharacterCount = 0;
        }
    }
    public void draw(){
        BufferedImage image = null;

        switch (direction){
            case "up":
                if(CharacterStep == 1){
                    image = up1;
                }
                if(CharacterStep == 2){
                    image = up2;
                }
                if(CharacterStep == 3){
                    image = up3;
                }
                if(CharacterStep == 4){
                    image = up4;
                }
                break;
            case "down":
                if(CharacterStep == 1){
                    image = down1;
                }
                if(CharacterStep == 2){
                    image = down2;
                }
                if(CharacterStep == 3){
                    image = down3;
                }
                if(CharacterStep == 4){
                    image = down4;
                }
                break;
            case "left":
                if(CharacterStep == 1){
                    image = left1;
                }
                if(CharacterStep == 2){
                    image = left2;
                }
                if(CharacterStep == 3){
                    image = left3;
                }
                if(CharacterStep == 4){
                    image = left4;
                }
                break;
            case "right":
                if(CharacterStep == 1){
                    image = right1;
                }
                if(CharacterStep == 2){
                    image = right2;
                }
                if(CharacterStep == 3){
                    image = right3;
                }
                if(CharacterStep == 4){
                    image = right4;
                }
                break;
        }

        StdDraw.picture(x, y, String.valueOf(image));
    }

}
