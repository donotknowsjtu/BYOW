import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class TransformTest {
    public static void main(String[] args){
        TETile[][] tiles = new TETile[3][4];
        for(int i = 0; i < tiles.length; i ++) {
            for (int j = 0; j < tiles[0].length; j ++) {
                tiles[i][j] = Tileset.WALL;
            }
        }
        tiles[2][1] = Tileset.FLOOR;
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(3, 4);
        teRenderer.renderFrame(tiles);
    }
}
