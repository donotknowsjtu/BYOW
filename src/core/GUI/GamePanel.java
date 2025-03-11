package core.GUI;

import core.EntityPackage.Player;
import core.GameGeneration.gamegeneration;
import core.GameGeneration.gamegeneration;
import utils.KeyHandler;

import javax.swing.*;
import java.awt.*;


//游戏内部窗口信息设置


public class GamePanel extends JPanel implements Runnable{
    //屏幕设置
    final int originalTileSize = 16;//设置默认图块尺寸
    final int scale = 2;//设置缩放比例

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 24;//设置24列屏幕比例长度
    final int maxScreenRow = 18;//设置18行屏幕比例长度
    final int screenWidth = tileSize * maxScreenCol;//确定窗口真实宽度
    final int screenHeight = tileSize * maxScreenRow;//确定真实长度（关于窗口大小，后期根据输入seed进行动态更改）

    //设置FPS
    int FPS = 60;


    Thread gameThread;

    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this,keyH);
    gamegeneration gg = new gamegeneration(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));//后期根据输入seed更改
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; //每0.01666秒绘制一次
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            //循环中维持游戏状态，并根据输入进行反馈更新

            //更新数据函数
            updata();
            //重绘图形函数
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void updata(){
        player.update();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;

        Graphics2D g2 = (Graphics2D)g;

        //地图绘制
        gg.TileType();
        for(int x = 0; x < gg.MT.length; x++) {
            for (int y = 0; y < gg.MT[x].length; y++) {
                if(gg.MT[x][y] != null) {
                    gg.MT[x][y].draw(g2);
                }
            }
        }

        //人物绘制
        player.draw(g2);
        g2.dispose();

    }
}