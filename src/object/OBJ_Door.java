package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(GamePanel gp){
        this.gp = gp;
        this.name = "Door";
        try{
            image1 = uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/door.png")), gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }


    }



}
