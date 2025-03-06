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
        RandomWorld();
    }


    // 渲染世界
    public void WorldRender(){
        TERenderer worldRender = new TERenderer();
        worldRender.initialize(this.LENGTH, this.WIDTH);
        worldRender.renderFrame(this.tiles);
    }

    // 根据seed随机生成地图
    private void RandomWorld(){
        // 先将地图的四条边进行生成，让这四条边上的瓦片只能生成墙壁或者边界，减少检测时的复杂性
        for (int x = 0; x < LENGTH; x ++){
            // 设置不同的边界值防止伪随机性导致每次生成的四个随机数完全相同

            // rand1决定最上面一行
            int rand1 = RandomSeed.nextInt(10);
            // rand2决定最下面一行
            int rand2 = RandomSeed.nextInt(100);

            if (rand1 < 4){
                tiles[x][WIDTH - 1] = Tileset.WALL;
            }else{
                tiles[x][WIDTH - 1] = Tileset.NOTHING;
            }
            if (rand2 < 40){
                tiles[x][0] = Tileset.WALL;
            }else{
                tiles[x][0] = Tileset.NOTHING;
            }

        }
        for (int x = 0; x < WIDTH; x++){
            // rand3决定最左边一列
            int rand3 = RandomSeed.nextInt(1000);
            // rand4决定最右边一列
            int rand4 = RandomSeed.nextInt(10000);

            if (rand3 < 400){
                tiles[0][x] = Tileset.WALL;
            }else{
                tiles[0][x] = Tileset.NOTHING;
            }
            if (rand4 < 4000){
                tiles[LENGTH - 1][x] = Tileset.WALL;
            }else{
                tiles[LENGTH - 1][x] = Tileset.NOTHING;
            }
        }

        for (int x = 1; x < LENGTH - 1; x ++){
            for(int y = 1; y < WIDTH - 1; y ++){
                int rand = RandomSeed.nextInt(100);
                // 生成边界
                if (rand >= 40 && detectSide(x, y)){
                    tiles[x][y] = Tileset.NOTHING;
                }
                /***bug !!! ***/
                // 生成墙壁
                else if ( rand > 30 && rand < 40 && detectWall(x, y)){
                    tiles[x][y] = Tileset.WALL;
                }
                /***bug !!! ***/
                // 生成地板
                else if (rand <= 30 && detectFloor(x, y)){
                    tiles[x][y] = Tileset.FLOOR;
                }
                /***bug !!! ***/
                else{
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    // 检测边界情况
    private boolean detectSide(int x, int y){

            if (tiles[x - 1][y] == Tileset.FLOOR || tiles[x + 1][y] == Tileset.FLOOR || tiles[x][y+1] == Tileset.FLOOR || tiles[x][y - 1] == Tileset.FLOOR){
                return false;
            }
        return true;
    }
    // 检测墙壁情况
    private boolean detectWall(int x, int y){
        if (tiles[x - 1][y] == Tileset.NOTHING && tiles[x + 1][y] == Tileset.NOTHING && tiles[x][y - 1] == Tileset.NOTHING && tiles[x][y + 1] == Tileset.NOTHING){
            return false;
        }
        if (tiles[x - 1][y] == Tileset.FLOOR && tiles[x + 1][y] == Tileset.FLOOR && tiles[x][y - 1] == Tileset.FLOOR && tiles[x][y + 1] == Tileset.FLOOR){
            return false;
        }
        return true;
    }
    // 检测地板情况
    private boolean detectFloor(int x, int y){
        if (tiles[x - 1][y] == Tileset.NOTHING || tiles[x + 1][y] == Tileset.NOTHING || tiles[x][y - 1] == Tileset.NOTHING || tiles[x][y + 1] == Tileset.NOTHING){
            return false;
        }
        return true;
    }
    public static void main(String args[]){
        new WorldGeneration(50, 50, 1234).WorldRender();
    }
}
