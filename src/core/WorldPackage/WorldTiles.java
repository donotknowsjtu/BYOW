package core.WorldPackage;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldTiles {
    // 世界的长度（x轴）
    public int LENGTH;
    // 世界的宽度（y轴）
    public int WIDTH;
    private long seed;
    public Random RandomSeed = new Random(seed);
   // 核心：瓦片构成的数组，长度为LENGTH，宽度为WIDTH
    public TETile[][] tiles;



    private final TERenderer worldRender = new TERenderer();

   // 用于存放生成的房间
    public List<Room> rooms = new ArrayList<>();


    // 构造器，根据传入的长和宽和种子依次调用外部生成函数、内部生成函数和内部确认连接函数，来对tiles进行修改，得到一个随机的tiles
    public WorldTiles(int LENGTH, int WIDTH, long seed){
        this.LENGTH = LENGTH;
        this.WIDTH = WIDTH;
        this.seed = seed;
        this.tiles = new TETile[LENGTH][WIDTH];

        OutsideGeneration();
        RoadGeneration();
        populateNOTHING();
    }

    /** @Test 仅供test使用
     * @Author 张书源 **/
    public WorldTiles(TETile[][] tiles, int seed, int LENGTH, int WIDTH, boolean isTest){
        if(isTest == true) {
            this.tiles = tiles;
            this.seed = seed;
            this.LENGTH = LENGTH;
            this.WIDTH = WIDTH;

            OutsideGeneration();
            RoadGeneration();
            populateNOTHING();
        }
    }


//    /**@Function 渲染世界
//     * @Description 通过调用TERenderer类的方法来生成一个与瓦片数组大小一致的窗口并且将瓦片数组渲染到窗口中
//     * @Usage 使用方法：需要在生成世界后，即创建WorldGeneration的实例后在需要的地方执行该实例的该方法来手动渲染世界，一般使用在程序的最后，用于手动渲染
//     */
//    public void WorldRender(){
//
//        worldRender.initialize(this.LENGTH, this.WIDTH);
//        worldRender.renderFrame(this.tiles);
//    }


    /** 生成边界
     * @Descrpiton 确定瓦片集中间点，根据seed随机选取若干点，根据角度依次连接点得到世界的边界（类型为WALL），再将边界以外的部分填充为NOTHING
     * @Author 王宇扬
     */
    private void OutsideGeneration(){
        RoomGenerate(RandomSeed.nextInt(5) + 25);
    }
    /*随机生成房间*/
    private void RoomGenerate(int RoomNums){
        while(rooms.size() < RoomNums)
        {
            int RoomWidth = RandomSeed.nextInt(8) + 4;
            int RoomHeight = RandomSeed.nextInt(8) + 4;

            int r_x = RandomSeed.nextInt(WIDTH - RoomWidth);
            int r_y = RandomSeed.nextInt(LENGTH - RoomHeight);

            Room room = new Room(r_x, r_y, RoomWidth, RoomHeight);
            boolean is_lap = false;
            for (Room r : rooms){
                if(room.is_intersect(r)){
                    is_lap = true;
                    break;
                }
            }

            if(!is_lap){
                rooms.add(room);
                /*绘制房间*/
                for(int I = room.r_x; I < room.r_x + room.r_width; I++){
                    for (int J = room.r_y; J < room.r_y + room.r_height; J++){
                        if(I == room.r_x || I == room.r_x + room.r_width - 1 ||
                                J == room.r_y || J == room.r_y + room.r_height - 1) {
                            tiles[I][J] = Tileset.WALL; // 绘制墙壁
                        } else {
                            tiles[I][J] = Tileset.FLOOR; // 内部填充地板
                        }
                    }
                }
            }
        }
    }


    /**
     * @Description 将房间连接起来，生成道路和道路两边的墙壁
     * @Author 张书源
     */
    private void RoadGeneration(){
        new Road(this).RoadGenerate(RandomSeed);
    }

    /**
     * 填充剩余瓦片为NOTHING
     * @Author 张书源
     *
     */

    public void populateNOTHING(){
        for(int i = 0; i < LENGTH; i ++){
            for(int j = 0;j < WIDTH; j ++){
                if(tiles[i][j] == null){
                    tiles[i][j] = Tileset.NOTHING;
                }
            }
        }
    }








    public TETile[][] getWorld() {
        return tiles;
    }
}






