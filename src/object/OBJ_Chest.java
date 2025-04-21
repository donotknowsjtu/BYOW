package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.name = "Chest";
        up1 = setup("/objects/chest.png", gp.tileSize, gp.tileSize);
        collisionOn = true;
    }
}
