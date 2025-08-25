package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(GamePanel gp){
        super();
        this.type = objectType;

        this.name = "Chest";
        down1 = setup("/objects/chest.png", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;
    }
}
