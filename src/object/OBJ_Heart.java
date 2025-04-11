package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/heart1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart2.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart3.png"));
            image1 = uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
