package core.GUI.Mode1;

import core.GUI.GamePanel;
import core.WorldPackage.WorldGeneration;
import tileengine.TETile;

import javax.swing.*;
import java.awt.*;

/**
 *
 * 根据MazeMode传入的参数生成世界
 *
  */
public class MazeMode {
    private long seed;
    private int Length;
    private int Width;
    private GamePanel gamePanel;


    public MazeMode(long seed, int Length, int Width){
        this.seed = seed;
        this.Length = Length;
        this.Width = Width;
        this.gamePanel = new GamePanel(Width, Length, seed);
    }

    // 新建MazeMode界面
    public void generate(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("逃离迷宫");

        GamePanel gamePanel = new GamePanel(this.Width, this.Length, this.seed);
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
