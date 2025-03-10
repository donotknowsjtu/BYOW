package core.GameGeneration;

import core.EntityPackage.MapTile;
import core.EntityPackage.Player;
import core.WorldPackage.WorldGeneration;
import tileengine.TETile;
import tileengine.Tileset;
import utils.KeyHandler;

import java.awt.*;
import java.util.Arrays;

public class gamegeneration {
    TETile[][] testTiles = new TETile[48][48];
    public MapTile[][] MT;

    public gamegeneration(){

        for (TETile[] row : testTiles) { //遍历每一行
            Arrays.fill(row, Tileset.NOTHING); // 再将每一行所有元素填充为NOTHING
        }

        MT = new MapTile[48][48];

        WorldGeneration worldGeneration = new WorldGeneration(testTiles, 1234, 48, 48, true);//测试暂用数据

    }




//根据世界数组确定该生成的砖块
    public void TileType(){
        for(int x = 0; x < testTiles.length; x++){
            for (int y = 0; y <testTiles[x].length; y++){
                if(testTiles[x][y] == Tileset.WALL){
                    MT[x][y] = new MapTile(x, y, 0);
                } else if (testTiles[x][y] == Tileset.FLOOR) {
                    MT[x][y] = new MapTile(x, y, 1);
                }
            }
        }
    }



}
