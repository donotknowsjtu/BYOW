package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.type = objectType;

        this.name = "Chest";
        down1 = setup("/objects/chest.png", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;
    }
}
