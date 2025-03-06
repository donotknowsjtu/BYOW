import core.WorldGeneration;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Arrays;

/** @Test for 内部和外部世界生成
 * @Created_time 2025.3.6
 * @Author 张书源
 * @Descripiton 由于junit的test包无法长时间运行程序，简言之就是程序无误就关闭程序了，然而我们的测试需要观察一下跑出来的结果，同时不需要设置预期结果，因为结果是随机的，所以测试没有使用test包
 */
public class GenerationTests {
    // test for InsideGeneration and OutsideGeneration
    public static void main(String[] args){
        TETile[][] testTiles = new TETile[30][10];
        for (TETile[] row : testTiles) { //遍历每一行
            Arrays.fill(row, Tileset.NOTHING); // 再将每一行所有元素填充为NOTHING
        }

        for (int x = 3; x < 7; x ++){
            for (int y = 3; y < 7; y ++){
              testTiles[x][y] = null;
            }
        }
        testTiles[3][3] = Tileset.FLOOR;

        WorldGeneration worldGeneration = new WorldGeneration(testTiles, 1234, 30, 10, true);
        worldGeneration.WorldRender();
    }
}
