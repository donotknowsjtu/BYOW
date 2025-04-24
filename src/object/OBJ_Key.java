package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp){
        super(gp);
        this.name = "Key";
        down1 = setup("/objects/key.png", gp.tileSize, gp.tileSize);
        collisionOn = true;
        description = "[钥匙]\n可以用来打开门\n钥匙的耐久只够打开一扇门";
    }


}
