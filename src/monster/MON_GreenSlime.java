package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        loadImage();
        type = monsterType;
    }


    public void loadImage(){
        up1 = setup("/player/_up1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/_up2.png", gp.tileSize, gp.tileSize);
        up3 = setup("/player/_up3.png", gp.tileSize, gp.tileSize);
        up4 = setup("/player/_up4.png", gp.tileSize, gp.tileSize);
        down1 = setup("/player/_down1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/_down2.png", gp.tileSize, gp.tileSize);
        down3 = setup("/player/_down3.png", gp.tileSize, gp.tileSize);
        down4 = setup("/player/_down4.png", gp.tileSize, gp.tileSize);
        left1 = setup("/player/_left1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/_left2.png", gp.tileSize, gp.tileSize);
        left3 = setup("/player/_left3.png", gp.tileSize, gp.tileSize);
        left4 = setup("/player/_left4.png", gp.tileSize, gp.tileSize);
        right1 = setup("/player/_right1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/_right2.png", gp.tileSize, gp.tileSize);
        right3 = setup("/player/_right3.png", gp.tileSize, gp.tileSize);
        right4 = setup("/player/_right4.png", gp.tileSize, gp.tileSize);
    }

    public void setAction(){
        super.setAction();
    }

    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
        this.speed = 4;
    }
}
