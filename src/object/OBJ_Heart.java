package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image1 = setup("/objects/heart1.png");
        image2 = setup("/objects/heart2.png");
        image3 = setup("/objects/heart3.png");
    }
}
