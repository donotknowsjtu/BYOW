package core.WorldPackage;
import core.GUI.GamePanel;
import core.EntityPackage.MapTile;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * 调用MapTile将通过GamePanel传入的世界参数生成的瓦片地图数组转化为MapTile数组
 */
public class WorldGeneration {
    GamePanel gp;
    TETile[][] tiles ;
    public MapTile[][] MT;
    public long seed;

    public WorldGeneration(GamePanel gp){
        this.seed = gp.seed;
        this.gp = gp;
        MT = new MapTile[gp.maxScreenCol][gp.maxScreenRow];
        WorldTiles worldTiles = new WorldTiles(gp.maxScreenCol, gp.maxScreenRow, seed);//测试暂用数据
        tiles = worldTiles.getWorld();
    }




    //根据世界数组确定该生成的砖块
    public void TileType(){
        int tileSize = gp.tileSize;
        for(int x = 0; x < tiles.length; x++){
            for (int y = 0; y < tiles[x].length; y++){
                if(tiles[x][y] == Tileset.WALL){
                    MT[x][y] = new MapTile(x * tileSize, y * tileSize, 0, tileSize ,true);
                } else if (tiles[x][y] == Tileset.FLOOR) {
                    MT[x][y] = new MapTile(x * tileSize, y * tileSize, 1, tileSize, false);
                } else if (tiles[x][y] == Tileset.NOTHING) {
                    MT[x][y] = null;
                }
            }
        }
    }



}
