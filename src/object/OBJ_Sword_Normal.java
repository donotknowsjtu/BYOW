package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        this.type = objectType;
        toUse = true;
        disappear = false;

        name = "Normal Sword";
        down1 = setup("/objects/sword_normal.png", gp.tileSize, gp.tileSize);
        image = down1;
        // solid area
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        // attack area
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;


        description = "[普通长剑]\n攻击范围为36，攻击倍率\n为1，木头采集率为0，\n肉类采集率为1";


    }
}
