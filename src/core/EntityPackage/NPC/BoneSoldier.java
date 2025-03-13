package core.EntityPackage.NPC;

import core.EntityPackage.Entity;
import core.EntityPackage.Player;
import core.GUI.GamePanel;
import utils.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * npc实体：骷髅兵
 * 功能：
 * 1.在指定范围内射箭
 * 2.向玩家行走
 */
public class BoneSoldier extends Entity {
    /**
     * 指针，指向调用BoneSoldier的GamePanel
     */
    GamePanel gp;
    Player player;
    private static final Random random = new Random();
    int[] player_location = new int[2];
    public BoneSoldier(GamePanel gp){
        this.gp = gp;
        this.direction = "up";
        CollisionRect = new Rectangle(3,6,10,10);
        getBoneSoldierImage();
        setDefaultValue();
    }

    /**
     * 设置初始位置
     */
    private void setDefaultValue(){
        x = 54;
        y = 80;
        speed = 2;
    }

    private void getBoneSoldierImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/BoneSoldier.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据玩家的位置移动
     */
    public void update(Player player){
        int[] direction = {player.x - this.x, player.y - this.y};
        isCollision = false;
        gp.CC.checker(this);
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

    public void draw(Graphics2D g2){
        g2.drawImage(up1, x, y, gp.tileSize, gp.tileSize, null);
    }

}
