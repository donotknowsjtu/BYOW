package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        this.type = objectType;

        name = "Heart";
        image1 = setup("/objects/heart1.png", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart2.png", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart3.png", gp.tileSize, gp.tileSize);
    }
}
