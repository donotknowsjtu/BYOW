package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp){
        super(gp);
        this.name = "Key";
        up1 = setup("/objects/key.png");
        collisionOn = true;

    }


}
