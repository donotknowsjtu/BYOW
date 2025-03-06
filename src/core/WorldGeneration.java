package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class WorldGeneration {
    // 世界的长度（x轴）
    private int LENGTH;
    // 世界的宽度（y轴）
    private int WIDTH;
    private int seed;
    private Random RandomSeed = new Random(seed);
   // 核心：瓦片构成的数组，长度为LENGTH，宽度为WIDTH
    public TETile[][] tiles;

    // 构造器，根据传入的长和宽和种子依次调用外部生成函数、内部生成函数和内部确认连接函数，来对tiles进行修改，得到一个随机的tiles
    public WorldGeneration(int LENGTH, int WIDTH, int seed){
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.seed = seed;
        this.tiles = new TETile[LENGTH][WIDTH];
        OutsideGeneration();
        InsideGeneration();
        ensureConnectivity();
    }

    /** @Test 仅供test使用
     * @Author 张书源 **/
    public WorldGeneration(TETile[][] tiles,int seed,int LENGTH, int WIDTH, boolean isTest){
        if(isTest == true) {
            this.tiles = tiles;
            this.seed = seed;
            this.LENGTH = LENGTH;
            this.WIDTH = WIDTH;
            OutsideGeneration();
            InsideGeneration();
            ensureConnectivity();
        }
    }


    /**@Function 渲染世界
     * @Description 通过调用TERenderer类的方法来生成一个与瓦片数组大小一致的窗口并且将瓦片数组渲染到窗口中
     * @Usage 使用方法：需要在生成世界后，即创建WorldGeneration的实例后在需要的地方执行该实例的该方法来手动渲染世界，一般使用在程序的最后，用于手动渲染
     */
    public void WorldRender(){
        TERenderer worldRender = new TERenderer();
        worldRender.initialize(this.LENGTH, this.WIDTH);
        worldRender.renderFrame(this.tiles);
    }


    /** 生成边界
     * @Descrpiton 确定瓦片集中间点，根据seed随机选取若干点，根据角度依次连接点得到世界的边界（类型为WALL），再将边界以外的部分填充为NOTHING
     * @Author 王宇扬
     */
    private void OutsideGeneration(){

    }

    /** 生成内部
     * @Author 张书源
     */
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

    /** 检测内部是否有被wall单独围起来的小区域，如果有将其转化为wall
    目的：通过杀死围栏，避免玩家被生成在这种围栏中
     @Author 张书源
     **/
    private void ensureConnectivity() {
        boolean[][] visited = new boolean[LENGTH][WIDTH];
        int largestRegionSize = 0;
        int largestRegionX = -1;
        int largestRegionY = -1;

        // 找到最大的连通区域
        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (tiles[x][y] == Tileset.FLOOR && !visited[x][y]) {
                    int regionSize = floodFill(x, y, visited);
                    if (regionSize > largestRegionSize) {
                        largestRegionSize = regionSize;
                        largestRegionX = x;
                        largestRegionY = y;
                    }
                }
            }
        }

        // 将非连通的小区域转换为WALL
        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (tiles[x][y] == Tileset.FLOOR && !visited[x][y]) {
                    tiles[x][y] = Tileset.WALL;
                }
            }
        }
    }
    /** @ensureConnectivity() 辅助方法，计算每个连通区域大小
        @Author 张书源
     **/
    private int floodFill(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= LENGTH || y < 0 || y >= WIDTH || visited[x][y] || tiles[x][y] != Tileset.FLOOR) {
            return 0;
        }
        visited[x][y] = true;
        int size = 1;
        size += floodFill(x + 1, y, visited);
        size += floodFill(x - 1, y, visited);
        size += floodFill(x, y + 1, visited);
        size += floodFill(x, y - 1, visited);
        return size;
    }

    public TETile[][] getWorld() {
        return tiles;
    }
}






