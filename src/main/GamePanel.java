package main;


import entity.Entity;
import entity.Player;
import entity.NPC_Soldier;
import object.SuperObject;
import org.apache.commons.lang3.ObjectUtils;
import tile.TileManage;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    public final int screenColumn = 20;
    public final int screenRow = 15;
    public final int pixel = 48 ;
    public final int tileSize = pixel ;
    public final int screenLength = screenColumn * tileSize;
    public final int screenWidth = screenRow * tileSize;

    public final int maxWorldCol = 50, maxWorldRow = 50;
    public final int maxWorldLength = maxWorldCol * tileSize;
    public final int maxWorldWidth = maxWorldRow * tileSize;

    private final int FPS = 30;
    private final double drawInterval  = 1000000000 / FPS;
    private double remainingTime;

    private Sound music;
    private Sound se;
    public TileManage tileManage;
    public Player player;
    public Entity[] npcs;
    public SuperObject[] objects;
    public UI ui;

    public KeyHandler keyHandler;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){
        this.setSize(screenLength, screenWidth);
        // 添加键盘监听器
        keyHandler = new KeyHandler(this);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        // 音频实例化
        music = new Sound();
        se = new Sound();
        // 玩家实例化
        this.player = new Player(this, keyHandler);

        // 瓦片控制器实例化
        this.tileManage = new TileManage(this);
        // 碰撞处理器实例化
        this.cChecker = new CollisionChecker(this);
        //
        this.aSetter = new AssetSetter(this);
        ui = new UI(this);

        //npc实例化
        npcs = new Entity[10];
        // objects实例化
        objects = new SuperObject[10];
    }
    public void setupGame(){
        // 将物品放置好
        aSetter.setObject();
        // 初始化npc
        aSetter.setNPC();

        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.run();

    }

    public void run(){
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null){
            update();

            repaint();

            try {
                remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void update(){
        if(gameState == playState) {
            player.update();
            for(Entity entity : npcs){
                if(entity != null){
                    entity.update();
                }
            }
            tileManage.update();
        } else if (gameState == pauseState) {
            
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // 绘制开始界面
        if(gameState == titleState){
            ui.draw(g2);
        }else {
            long drawStart = 0;
            drawStart = System.nanoTime();
            // 绘制地图
            tileManage.draw(g2);
            // 绘制道具
            for (SuperObject object : objects) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
            // 绘制玩家
            player.draw(g2);
            //绘制npc
            for (int i = 0; i < npcs.length; i++) {
                if (npcs[i] != null) {
                    npcs[i].draw(g2);
                }
            }
            // UI绘制
            ui.draw(g2);
            if (keyHandler.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passedTime = drawEnd - drawStart;
                g2.setColor(Color.WHITE);
                g2.drawString("Draw Time : " + passedTime, 10, 400);
                System.out.println("Draw Time : " + passedTime);
            }
        }

        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
