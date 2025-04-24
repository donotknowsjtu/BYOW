package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        down1 = setup("/objects/shield_wood.png", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        attack = 5;
        defense = 0;
        description = "[木盾]\n提供额外的防御力，防御倍率\n为1";
    }
}
