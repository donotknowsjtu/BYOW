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
    int[] player_location = new int[2];
    public BoneSoldier(GamePanel gp){
        this.gp = gp;
        getBoneSoldierImage();
        setDefaultValue();
    }

    /**
     * 设置初始位置
     */
    private void setDefaultValue(){
        x = 100;
        y = 100;
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
        if(new Random().nextInt(10) < 5){
            if(direction[0] != 0){
                this.x += (int) Math.signum(direction[0]) * speed;
            }
        }else {
            if(direction[1] != 0){
                this.y += (int) Math.signum(direction[1]) * speed;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(up1, x, y, gp.tileSize, gp.tileSize, null);
    }

}
