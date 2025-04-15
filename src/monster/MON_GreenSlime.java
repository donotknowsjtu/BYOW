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
        up1 = setup("/player/_up1.png");
        up2 = setup("/player/_up2.png");
        up3 = setup("/player/_up3.png");
        up4 = setup("/player/_up4.png");
        down1 = setup("/player/_down1.png");
        down2 = setup("/player/_down2.png");
        down3 = setup("/player/_down3.png");
        down4 = setup("/player/_down4.png");
        left1 = setup("/player/_left1.png");
        left2 = setup("/player/_left2.png");
        left3 = setup("/player/_left3.png");
        left4 = setup("/player/_left4.png");
        right1 = setup("/player/_right1.png");
        right2 = setup("/player/_right2.png");
        right3 = setup("/player/_right3.png");
        right4 = setup("/player/_right4.png");
    }

    public void setAction(){
        super.setAction();
    }
}
