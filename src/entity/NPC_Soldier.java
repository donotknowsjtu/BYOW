package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_Soldier extends Entity{

    private Random random;

    public NPC_Soldier(GamePanel gp){
        super(gp);
        this.speed = 1;
        this.direction = Direction.UP;
        this.stepCount = 1;
        this.solidArea = new Rectangle(8, 12, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.random = new Random(1234);
        loadImage();
        this.image = getImage();
        setDialogue();
    }

    private void loadImage(){
        up1 = setup("/NPC/soldier/_up1.png");
        up2 = setup("/NPC/soldier/_up2.png");
        up3 = setup("/NPC/soldier/_up3.png");
        up4 = setup("/NPC/soldier/_up4.png");
        down1 = setup("/NPC/soldier/_down1.png");
        down2 = setup("/NPC/soldier/_down2.png");
        down3 = setup("/NPC/soldier/_down3.png");
        down4 = setup("/NPC/soldier/_down4.png");
        left1 = setup("/NPC/soldier/_left1.png");
        left2 = setup("/NPC/soldier/_left2.png");
        left3 = setup("/NPC/soldier/_left3.png");
        left4 = setup("/NPC/soldier/_left4.png");
        right1 = setup("/NPC/soldier/_right1.png");
        right2 = setup("/NPC/soldier/_right2.png");
        right3 = setup("/NPC/soldier/_right3.png");
        right4 = setup("/NPC/soldier/_right4.png");

    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = Direction.UP;
            } else if (i <= 50) {
                direction = Direction.DOWN;
            } else if (i <= 75) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
            actionLockCounter = 0;
        }
    }

    public void setDialogue(){
        dialogues[0] = "Hello, man.";
        dialogues[1] = "So you come to this island \nto find the treasure?";
        dialogues[2] = "I used to be a great wizard but now ... \nI'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you.";

    }

    @Override  
    public void speak(){
        super.speak();
    }




}
