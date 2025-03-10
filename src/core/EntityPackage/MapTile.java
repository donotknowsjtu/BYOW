package core.EntityPackage;

import net.sf.saxon.expr.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MapTile extends Entity{
    int TileType;//地图砖块类型（0为墙，1为地板）（后续新定义再追加）
    BufferedImage Wall, Floor;

    public MapTile(int x , int y ,int TY){
        this.x = x;
        this.y = y;
        this.TileType = TY;

        getPlayerImage();
    }

    public void getPlayerImage(){
        try{
            Wall = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/wall.png")));
            Floor = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/floor.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D GM){
        BufferedImage M_image = null;
        if(TileType == 0) {
            M_image = Wall;
        } else if (TileType == 1) {
            M_image = Floor;
        }
        //GM.drawImage(M_image, x, y, );

    }
}
