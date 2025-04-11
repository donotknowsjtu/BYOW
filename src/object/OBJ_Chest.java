package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject{

    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try {
            image1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")), gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
