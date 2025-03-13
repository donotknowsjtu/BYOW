package core.EntityPackage;

import core.GUI.GamePanel;

import utils.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp , KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValue();
        getPlayerImage();

        CollisionRect = new Rectangle();
        CollisionRect.x = 3;
        CollisionRect.y = 6;
        CollisionRect.width = 6;
        CollisionRect.height = 6;
    }


    //设置初始位置，后期根据房间坐标而更改（考虑坐标修改转移到初始化中）
    public void setDefaultValue(){
        x = 50;
        y = 100;
        speed = 2;
        direction = "up";
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

        if(keyH.upPressed ||keyH.downPressed ||keyH.leftPressed ||keyH.rightPressed ) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            }
            isCollision = false;
            gp.CC.checker(this);

            if (!isCollision) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }

            }

            CharacterCount++;
            if (CharacterCount > 12) {
                if (CharacterStep < 4) {
                    CharacterStep++;
                } else {
                    CharacterStep = 1;
                }
                CharacterCount = 0;
            }
        }



    }

    public void draw(Graphics2D g2){
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

        // bug可能是stddraw库不支持这种格式的图片，将图片格式进行转化
        //BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //Graphics2D g = convertedImage.createGraphics();
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        //g.dispose();


//        StdDraw.picture(x, y, String.valueOf(image));
    }

}
