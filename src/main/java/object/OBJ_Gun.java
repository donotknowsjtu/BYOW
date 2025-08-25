package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Gun extends Entity {



    public OBJ_Gun(GamePanel gp) {
        super();
        type = objectType;
        name = "Gun";
        down1 = setup("/objects/gun.png", gp.tileSize, gp.tileSize);
        image = down1;
        toUse = true;
        disappear = false;
        // solid area
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        // attack
        attackValue = 10;
        description = "[枪]\n攻击范围为，攻击倍率\n为10";

    }
}
