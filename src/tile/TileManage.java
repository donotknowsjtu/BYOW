package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManage {
    private GamePanel gp;
    public Tile[] tileType;
    public int[][] map;
    private int drawMinCol, drawMaxCol, drawMinRow, drawMaxRow;
    public int worldLength, worldWidth;

    public TileManage(GamePanel gp){
        this.gp = gp;
        this.map = new int[gp.maxWorldLength][gp.maxWorldWidth];

        this.drawMinCol = (gp.player.worldX - gp.screenLength / 2 - gp.tileSize * 4) / gp.tileSize;
        this.drawMaxCol = (gp.player.worldX + gp.screenLength / 2 + gp.tileSize * 4) / gp.tileSize;
        this.drawMinRow = (gp.player.worldY - gp.screenWidth / 2 - gp.tileSize * 3) / gp.tileSize;
        this.drawMaxRow = (gp.player.worldY + gp.screenWidth / 2 + gp.tileSize * 3) / gp.tileSize;


        tileType = new Tile[10];
        readPicture();
        loadMap("/Map/map01.txt");
        this.worldLength = map.length;
        this.worldWidth = map[0].length;
    }

    private void readPicture(){

        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);


    }

    public void setup(int index, String imageName, boolean collisionOn){
        UtilityTool uTool = new UtilityTool();
        try{
            tileType[index] = new Tile();
            tileType[index].image = ImageIO.read(getClass().getResourceAsStream("/Map/" + imageName + ".png"));
            tileType[index].image = uTool.scaleImage(tileType[index].image, gp.tileSize, gp.tileSize);
            tileType[index].collisonOn = collisionOn;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadMap(String filename){
        try{
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filename));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while(col < gp.maxWorldCol){
                    int num = Integer.parseInt(numbers[col]);
                    map[col][row] = num;
                    col ++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row ++;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){
       int worldCol = 0, worldRow = 0;

       while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

//           if(worldCol > gp.player.worldCol - gp.screenColumn / 2 - 2 && worldCol < gp.player.worldCol + gp.screenColumn / 2 + 2
//                   && worldRow > gp.player.worldRow - gp.screenRow / 2 - 2 && worldRow < gp.player.worldRow + gp.screenRow / 2 + 2
//           ) {

               int tileNum = map[worldCol][worldRow];
               int worldX = worldCol * gp.tileSize;
               int worldY = worldRow * gp.tileSize;

               int screenX = worldX - gp.player.worldX + gp.player.screenX;
               int screenY = worldY - gp.player.worldY + gp.player.screenY;

           if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                   worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
               g2.drawImage(tileType[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//           }
           }

           worldCol++;
           if (worldCol == gp.maxWorldCol) {
               worldCol = 0;
               worldRow++;
           }
       }
    }

    public void update() {
        if(gp.keyHandler.reloadMap){
            loadMap("/Map/map01.txt");

        }
        this.drawMinCol = (gp.player.worldX - gp.screenLength / 2 - gp.tileSize * 4) / gp.tileSize;
        this.drawMaxCol = (gp.player.worldX + gp.screenLength / 2 + gp.tileSize * 4) / gp.tileSize;
        this.drawMinRow = (gp.player.worldY - gp.screenWidth / 2 - gp.tileSize * 3) / gp.tileSize;
        this.drawMaxRow = (gp.player.worldY + gp.screenWidth / 2 + gp.tileSize * 3) / gp.tileSize;

    }
}
