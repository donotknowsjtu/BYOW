package core;


import entity.Entity;
import entity.Player;
import gameContext.*;
import lombok.Getter;
import monster.MON_GreenSlime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tile.TileManage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

@Component("gamePanel")
@Scope("prototype") // 每次注入都会创建一个新的实例
public class GamePanel extends JPanel implements Runnable, CoreContext, InputContext, InitializingBean, StateContext, AudioContext {

    Thread gameThread;


    private final int FPS = 30;
    private final double drawInterval  = 1000000000 / FPS;
    private double remainingTime;
    @Autowired
    private Sound music; // 背景音乐
    private Sound se; // 音效
    public TileManage tileManage;
    public Player player;
    public Entity[] npcs;
    public Entity[] objects;
    public Entity[] monsters;
    private ArrayList<Entity> entityList = new ArrayList<>();

    public UI ui;
    // 键盘监听器
    @Autowired
    public KeyHandler keyHandler;
    // 鼠标监听器
    @Autowired
    public MouseHandler mouseHandler;
    @Getter
    private Point mousePoint;
    @Getter
    private double mouseAngle;

    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public EventHandler eHandler;

    // GAME STATE
    private int gameState;


    // panel中心点坐标
    @Getter
    private Point panelCenter;


    public GamePanel(){

        this.setSize(screenLength, screenWidth);

        // 添加键盘监听器
        keyHandler = new KeyHandler(this);
        this.addKeyListener(keyHandler);

        // 添加鼠标监听器
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.setCursor(mouseHandler.createInvisibleCursor());
        mousePoint = mouseHandler.getMousePoint();
        mouseAngle = mouseHandler.getMouseAngle();

        this.setFocusable(true);
        // 音频实例化
        music = new Sound();
        se = new Sound();
        // 玩家实例化
        this.player = new Player(this);

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
        // panel中心点
        panelCenter = new Point(screenLength/2, screenWidth/2);
    }

    // 初始化游戏状态
    @Override
    public void afterPropertiesSet() {

        // 初始化键鼠监听器
        keyHandler.setStateContext(this);
        keyHandler.setAudioContext(this);
        mouseHandler.setStateContext(this);
        mouseHandler.setAudioContext(this);
        // 初始化玩家实例
        player.setInputContext(this);
        player.setAudioContext(this);

        // 初始化游戏
        setupGame();
        // 启动游戏线程
        startGameThread();
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
        gameThread.start();

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
        if(keyHandler.mute){
            if(music.isPlaying()){
                stopMusic();
            }
        }else{
            playMusic(0);

        }
        if(gameState == playState) {
            // 更新鼠标位置和角度
            mouserUpdate();

            player.update();
            for(Entity npc : npcs){
                if(npc != null){
                    npc.update();
                }
            }
            for(int i = 0; i < monsters.length; i ++){
                if(monsters[i] != null){
                    if(monsters[i].alive && !monsters[i].dying){
                        monsters[i].update();
                    }
                    if(!monsters[i].alive){
                        // npc 死亡
//                        monsters[i] = null;
                        monsters[i].alive = true;
                        monsters[i].life = monsters[i].maxLife;
                    }
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
            mouseHandler.draw(g2);
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
                entityList.get(i).draw(g2, entityList.get(i).type);
            }
            // 移除entity，下次绘制时重新添加
            entityList.clear();

            // UI绘制
            ui.draw(g2);

            // 鼠标绘制
            mouseHandler.draw(g2);
        }

        g2.dispose();
    }

    // Audiocontext
    @Override
    public void playMusic(int i){
        music.playMusic(i);
    }
    @Override
    public void stopMusic(){
        music.stopMusic();
    }
    @Override
    public void playSE(int i){
        se.playSE(i);
    }
    private void mouserUpdate(){
        mouseAngle = mouseHandler.getMouseAngle();
        mousePoint = mouseHandler.getMousePoint();
    }


    // StateContext
    @Override
    public int getGameState() {
       return gameState;
    }
    @Override
    public void setGameState(int state) {
        if(state == titleState || state == playState || state == pauseState || state == dialogueState) {
            this.gameState = state;
        } else {
            throw new IllegalArgumentException("Invalid game state: " + state);
        }
    }
}
