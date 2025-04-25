package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp){
        super(gp);
        this.type = objectType;
        toUse = false;
        disappear = false;

        this.name = "Key";
        down1 = setup("/objects/key.png", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;
        description = "[钥匙]\n可以用来打开门\n钥匙的耐久只够打开一扇门";

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }


}
