package entity;

import main.GamePanel;

import java.awt.*;

public class NPC_Soldier extends Entity{



    public NPC_Soldier(GamePanel gp){
        super(gp);
        this.speed = 1;
        this.direction = Direction.UP;
        this.spriteCounter = 1;
        this.solidArea = new Rectangle(8, 12, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        loadImage();
        this.image = getImage();
        setDialogue();
    }

    private void loadImage(){
        up1 = setup("/NPC/soldier/_up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/soldier/_up2.png", gp.tileSize, gp.tileSize);
        up3 = setup("/NPC/soldier/_up3.png", gp.tileSize, gp.tileSize);
        up4 = setup("/NPC/soldier/_up4.png", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/soldier/_down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/soldier/_down2.png", gp.tileSize, gp.tileSize);
        down3 = setup("/NPC/soldier/_down3.png", gp.tileSize, gp.tileSize);
        down4 = setup("/NPC/soldier/_down4.png", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/soldier/_left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/soldier/_left2.png", gp.tileSize, gp.tileSize);
        left3 = setup("/NPC/soldier/_left3.png", gp.tileSize, gp.tileSize);
        left4 = setup("/NPC/soldier/_left4.png", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/soldier/_right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/soldier/_right2.png", gp.tileSize, gp.tileSize);
        right3 = setup("/NPC/soldier/_right3.png", gp.tileSize, gp.tileSize);
        right4 = setup("/NPC/soldier/_right4.png", gp.tileSize, gp.tileSize);

    }

    @Override
    public void setAction() {
        super.setAction();
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
