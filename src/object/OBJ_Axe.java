package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(GamePanel gp){
        super(gp);

        type = objectType;
        name = "Normal Axe";
        down1 = setup("/objects/axe.png", gp.tileSize, gp.tileSize);
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
        // attack area
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;

        description = "[普通斧子]\n攻击范围为30，攻击倍率\n为2，木头采集率为1，\n肉类采集率为0.5";


    }
}
