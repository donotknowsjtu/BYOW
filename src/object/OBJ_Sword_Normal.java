package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        down1 = setup("/objects/sword_normal.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[普通长剑]\n提供额外的攻击力，攻击倍率\n为1";
    }
}
