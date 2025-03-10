package core.GUI.Mode1;

import core.WorldPackage.WorldGeneration;
import tileengine.TETile;

/**
 *
 * 根据MazeMode传入的参数生成世界
 *
  */
public class MazeMode {
    private long seed;
    private int Length;
    private int Width;

    public MazeMode(long seed, int Length, int Width){
        this.seed = seed;
        this.Length = Length;
        this.Width = Width;
    }

    /**
     * 生成世界和实体
     */
    public void generate(){
        WorldGeneration world = new WorldGeneration(Length, Width, seed);
        TETile [][] worldTiles = world.getWorld();


    }
}
