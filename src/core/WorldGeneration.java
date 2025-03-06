package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class WorldGeneration {
    private int LENGTH;
    private int WIDTH;
    private int seed;
    private Random RandomSeed = new Random(seed);
    public TETile[][] tiles;

    public WorldGeneration(int LENGTH, int WIDTH, int seed){
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.seed = seed;
        this.tiles = new TETile[LENGTH][WIDTH];
        OutsideGeneration();
        InsideGeneration();
    }


    // 渲染世界
    public void WorldRender(){
        TERenderer worldRender = new TERenderer();
        worldRender.initialize(this.LENGTH, this.WIDTH);
        worldRender.renderFrame(this.tiles);
    }



    // 生成边界 by 王宇扬
    private void OutsideGeneration(){

    }

    // 生成内部 by 张书源
    private void InsideGeneration(){
       for(int x = 0; x < this.LENGTH; x ++){
           for(int y = 0; y < this.WIDTH; y ++){
               if(tiles[x][y] == null){
                   if(RandomSeed.nextInt(100) < 50){
                       tiles[x][y] = Tileset.FLOOR;
                   }else{
                       tiles[x][y] = Tileset.WALL;
                   }
               }
           }
       }
    }



}
