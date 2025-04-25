package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp){
        super(gp);
        this.type = objectType;

        this.name = "Door";
        down1 = setup("/objects/door.png", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }



}
