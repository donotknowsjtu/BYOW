package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp){

        super(gp);
        this.name = "Door";

        up1 = setup("/objects/door.png", gp.tileSize, gp.tileSize);
        collisionOn = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }



}
