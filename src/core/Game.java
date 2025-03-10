package core;

import core.EntityPackage.Player;
import core.WorldPackage.WorldGeneration;
import tileengine.TERenderer;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.Arrays;

public class Game {
    private TERenderer ter = new TERenderer();
    private Player player;
    WorldGeneration wg;
    public Game(){}
    public void initialize(){
        TETile[][] testTiles = new TETile[48][48];
        for (TETile[] row : testTiles) {
            Arrays.fill(row, Tileset.NOTHING);
        }
        wg = new WorldGeneration(testTiles, 1234, 48, 48, true);
        ter.initialize(48,48);


        Point startPoint = wg.rooms.get(0).get_center();
        player = new Player(startPoint.x + 0.5,startPoint.y + 0.5);
    }
    public void run(){
        while (true){
            player.update();

            render();

            StdDraw.pause(16);

        }
    }

    private void render(){
        ter.renderFrame(wg.tiles);
        player.draw();
        StdDraw.show();
    }
}
