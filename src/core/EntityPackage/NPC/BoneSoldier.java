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
        CollisionRect = new Rectangle(3,6,6,6);
        getBoneSoldierImage();
        setDefaultValue();
    }

    /**
     * 设置初始位置
     */
    private void setDefaultValue(){
        x = 54;
        y = 80;
        speed = 4;
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
    public void update(Player player) {
        int[] direction = new int[]{player.x - this.x, player.y - this.y};
        isCollision = false;

        while (this.x != player.x || this.y != player.y) {
            direction[0] = player.x - this.x;
            direction[1] = player.y - this.y;
            if (random.nextInt(11) < 3) {
                if(direction[1] <= 0){continue;}
                this.direction = "up";
                gp.CC.checker(this);
                if (!isCollision) {
                    this.y += speed;
                }
            } else if (random.nextInt(11) < 6) {
                if(direction[1] >= 0){continue;}
                this.direction = "down";
                gp.CC.checker(this);
                if (!isCollision) {
                    this.y -= speed;
                }
            } else if (random.nextInt(11) < 9) {
                if(direction[0] >= 0){continue;}
                this.direction = "left";
                gp.CC.checker(this);
                if (!isCollision) {
                    this.x -= speed;
                }
            } else {
                if(direction[0] <= 0){continue;}
                this.direction = "right";
                gp.CC.checker(this);
                if (!isCollision) {
                    this.x += speed;
                }

            }
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(up1, x, y, gp.tileSize, gp.tileSize, null);
    }

}
