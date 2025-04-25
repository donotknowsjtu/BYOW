package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp){
        super(gp);
        this.type = objectType;

        name = "Boots";
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;
    }

}
