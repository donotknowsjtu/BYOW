import core.WorldPackage.WorldGeneration;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Arrays;

public class RoomGenerationTests {
        public static void main(String[] args){
            TETile[][] testTiles = new TETile[48][48];
            for (TETile[] row : testTiles) { //遍历每一行
                Arrays.fill(row, Tileset.NOTHING); // 再将每一行所有元素填充为NOTHING
            }


            WorldGeneration worldGeneration = new WorldGeneration(testTiles, 1234, 48, 48, true);
            //worldGeneration.WorldRender();
        }
}

