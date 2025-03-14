package core.EntityPackage.NPC;

import core.EntityPackage.Entity;
import core.EntityPackage.Player;
import core.GUI.MazeModeJFrame.JPanel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * npc实体：骷髅兵
 * 功能：
 * 1.在指定范围内射箭
 * 2.向玩家行走
 */
public class Knight extends Entity {
    /**
     * 指针，指向调用BoneSoldier的GamePanel
     */
    GamePanel gp;
    private Random random ;
    int ManHatonDistance = 80;
    private int[] default_location = new int[2];
    // 骑士种子，避免骑士位置重复
    private int knight_seed;

    public Knight(GamePanel gp, int knight_seed){
        this.gp = gp;
        this.direction = "up";
        this.knight_seed = knight_seed;
        this.random = new Random(knight_seed);
        default_location = setDefaultLocation();
        CollisionRect = new Rectangle(3,6,6,6);
        getBoneSoldierImage();
        setDefaultValue();
    }

    /**
     * 设置默认位置
     * @return 二维数组
     */
    private int[] setDefaultLocation() {
        Boolean terminate = false;
        int x_loc = 0, y_loc = 0;
        while (!terminate){
            x_loc = random.nextInt(gp.maxScreenCol);
            y_loc = random.nextInt(gp.maxScreenRow);
            if(gp.wg.MT[x_loc][y_loc] == null){
                continue;
            } else if (gp.wg.MT[x_loc][y_loc].TileType == 1) {
                terminate = true;
            }
        }
        return new int[]{x_loc, y_loc};
    }

    /**
     * 设置初始位置
     */
    private void setDefaultValue(){
        x = default_location[0];
        y = default_location[1];
        speed = 4;
    }

    private void getBoneSoldierImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/knight.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
            判断人物和骑士之间的距离是否小于设定值
     *
     */
    private Boolean detectDistance(Player player){
        if((Math.abs(player.x - this.x) + Math.abs(player.y - this.y)) > ManHatonDistance){return false;}
        return true;
    }
    /**
     * 根据玩家的位置移动
     */
    public void update(Player player){

        if(detectDistance(player)){
            int[] direction = {player.x - this.x, player.y - this.y};
            isCollision = false;
            // 概率开挂穿墙，概率为五十
            if(random.nextInt(20) < 10){gp.CC.checker(this);}

            if(direction[0] < 0 ){
                this.direction = "left";
            } else if (direction[0] > 0) {
                this.direction = "right";
            }
            if(direction[1] < 0){
                this.direction = "up";
            } else if (direction[1] > 0) {
                this.direction = "down";
            }
            if(!isCollision) {
                if (random.nextInt(10) < 5) {
                    this.x += (int) Math.signum(direction[0]) * speed;
                } else {
                    this.y += (int) Math.signum(direction[1]) * speed;
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(up1, x, y, gp.tileSize, gp.tileSize, null);
    }

}
