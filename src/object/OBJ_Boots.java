package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject{

    public OBJ_Boots(GamePanel gp){
        this.gp = gp;
        name = "Boots";
        try {
            image1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/boots.png")), gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
