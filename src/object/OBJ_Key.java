package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject{

    public OBJ_Key(GamePanel gp){
        this.gp = gp;
        this.name = "Key";
        try{
            image1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")), gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
