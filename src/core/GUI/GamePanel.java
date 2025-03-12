package core.GUI;

import core.Collisionchecker.CollisionChecker;
import core.EntityPackage.NPC.BoneSoldier;
import core.EntityPackage.Player;
import core.GameGeneration.gamegeneration;

import org.checkerframework.checker.units.qual.C;
import utils.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


//游戏内部窗口信息设置


public class GamePanel extends JPanel implements Runnable{
    //屏幕设置
    final int originalTileSize = 16;//设置默认图块尺寸
    final int scale = 1;//设置缩放比例

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 48;//设置24列屏幕比例长度
    final int maxScreenRow = 48;//设置18行屏幕比例长度
    final int screenWidth = tileSize * maxScreenCol;//确定窗口真实宽度
    final int screenHeight = tileSize * maxScreenRow;//确定真实长度（关于窗口大小，后期根据输入seed进行动态更改）

    //设置FPS
    int FPS = 120;


    Thread gameThread;


    // 新增缓冲图像
    private BufferedImage staticMapBuffer;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this,keyH);
        // npc
    BoneSoldier BoneSoldier1 = new BoneSoldier(this);
    gamegeneration gg = new gamegeneration(this);
    public CollisionChecker CC = new CollisionChecker(this, gg);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));//后期根据输入seed更改
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        generateStaticMapBuffer();// 初始化时预渲染静态地图

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



    // 预渲染静态地图到缓冲图像
    private void generateStaticMapBuffer() {
        staticMapBuffer = new BufferedImage(
                screenWidth,
                screenHeight,
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = staticMapBuffer.createGraphics();

        // 调用地图生成逻辑（确保 gg.TileType() 只生成静态内容）
        gg.TileType();

        // 绘制静态元素到缓冲
        for(int x = 0; x < gg.MT.length; x++) {
            for (int y = 0; y < gg.MT[x].length; y++) {
                if(gg.MT[x][y] != null) {
                    gg.MT[x][y].draw(g2d);
                }
            }
        }
        g2d.dispose();
    }


    public void updata(){
        player.update();
        BoneSoldier1.update(player);
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;

        Graphics2D g2 = (Graphics2D)g;

        g2.drawImage(staticMapBuffer, 0, 0, null);

        //人物绘制
        player.draw(g2);
        // npc绘制
        BoneSoldier1.draw(g2);
        g2.dispose();

    }
}