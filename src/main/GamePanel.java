package main;


import entity.Entity;
import entity.Player;
import monster.MON_GreenSlime;
import tile.TileManage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public Entity[] objects;
    public Entity[] monsters;
    private ArrayList<Entity> entityList = new ArrayList<>();

    public UI ui;

    public KeyHandler keyHandler;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public EventHandler eHandler;

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
        objects = new Entity[10];
        // monster实例化
        monsters = new Entity[10];
        // 事件处理器实例化
        eHandler = new EventHandler(this);
    }
    public void setupGame(){
        // 将物品放置好
        aSetter.setObject();
        // 初始化npc
        aSetter.setNPC();
        // 初始化Monster
        aSetter.setMonster();
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
            for(Entity npc : npcs){
                if(npc != null){
                    npc.update();
                }
            }
            for(Entity monster : monsters){
                if(monster != null){
                    monster.update();
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

            // 绘制地图
            tileManage.draw(g2);
            // 绘制entity
            entityList.add(player);
            for(int i = 0; i < npcs.length;i ++){
                if(npcs[i] != null){
                    entityList.add(npcs[i]);
                }
            }
            for(int i = 0; i < objects.length;i ++){
                if(objects[i] != null){
                    entityList.add(objects[i]);
                }
            }
            for(int i = 0; i < monsters.length;i ++){
                if(monsters[i] != null){
                    entityList.add(monsters[i]);
                }
            }
            // 对渲染对象进行分类,按照纵坐标从上至下排序
            Collections.sort(entityList, new Comparator<Entity>(){

                @Override
                public int compare(Entity e1, Entity e2){
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });
            // 绘制Entity
            for(int i = 0; i < entityList.size(); i ++){
                entityList.get(i).draw(g2);
            }
            // 移除entity，下次绘制时重新添加
            entityList.clear();

            // UI绘制
            ui.draw(g2);

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
