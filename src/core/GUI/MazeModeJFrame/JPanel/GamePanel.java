package core.GUI.MazeModeJFrame.JPanel;

import core.Collisionchecker.CollisionChecker;
import core.EntityPackage.NPC.BoneSoldier;
import core.EntityPackage.NPC.Knight;
import core.EntityPackage.Player;
import core.WorldPackage.WorldGeneration;

import utils.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


//游戏内部窗口信息设置


public class GamePanel extends JPanel implements Runnable{
    //屏幕设置
    final int originalTileSize = 16;//设置默认图块尺寸
    final int scale = 1;//设置缩放比例
    public final int tileSize = originalTileSize * scale;




    public int maxScreenCol ;//列数
    public int maxScreenRow ;//行数
    final int screenWidth; //确定窗口真实宽度
    final int screenHeight;//确定真实长度（关于窗口大小，后期根据输入seed进行动态更改）
    public long seed; // 种子
    //设置FPS
    int FPS = 120;
    // 玩家
    Player player;
    // npc
    BoneSoldier BoneSoldier1;
     // 骑士数量
    int Knights_num;
    ArrayList<Knight> Knights;
     // 骑士种子，用于生成不同位置的骑士
    int knight_seed;
    // 碰撞处理类
    public CollisionChecker CC;
    // gamegeneration
    public WorldGeneration wg;
    Thread gameThread;

    // 新增缓冲图像
    private BufferedImage staticMapBuffer;
    KeyHandler keyH = new KeyHandler();

    /**
     *
     * 根据玩家传入的地图行数和列数和种子来生成地图
     * @param maxScreenRow 行数
     * @param maxScreenCol 列数
     */
    public GamePanel(int maxScreenRow, int maxScreenCol, long seed){
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        /**
         * 非常重要！！！，初始化一个元素前如果这个元素就被别的元素使用了，那么必须重新初始化唉巴拉巴拉我说不明白反正就报错了
         */
        screenWidth = tileSize * maxScreenCol;
        screenHeight = tileSize * maxScreenRow;

        wg = new WorldGeneration(this);
        CC = new CollisionChecker(this, wg);


        player = new Player(this,keyH);
        // npc
        BoneSoldier1 = new BoneSoldier(this);
        // 骑士数量暂定为3
//        Knights_num = 3;
//        knight_seed = 1234;
//        for(int i = 0; i < Knights_num; i ++){
//            Knights.add(new Knight(this, knight_seed));
//            knight_seed += 100;
//        }




        this.seed = seed;
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

        // 调用地图生成逻辑（确保 wg.TileType() 只生成静态内容）
        wg.TileType();

        // 绘制静态元素到缓冲
        for(int x = 0; x < wg.MT.length; x++) {
            for (int y = 0; y < wg.MT[x].length; y++) {
                if(wg.MT[x][y] != null) {
                    wg.MT[x][y].draw(g2d);
                }
            }
        }
        g2d.dispose();
    }


    public void updata(){
        player.update();
        BoneSoldier1.update(player);
//        for(Knight knight : Knights){
//            knight.update(player);
//        }
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
//        for(Knight knight : Knights){
//            knight.draw(g2);
//        }
        g2.dispose();

    }
}