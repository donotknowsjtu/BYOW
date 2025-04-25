package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion extends Entity {
    private int variety;
    private final int bluePotion = 1;
    private final int redPotion = 2;

    /**
     * 药水瓶的构造函数
     * @param gp
     * @param variety
     * 选择要构造的药水瓶的种类,
     *  类型为bluePotion = 1,
     *  redPotion = 2
     */
    public OBJ_Potion(GamePanel gp, int variety){
        super(gp);

        type = objectType;
        toUse = true;
        disappear = true;

        if(variety == bluePotion || variety == redPotion){
            this.variety = variety;
        }
        else{
            throw new IllegalArgumentException("输入的药水瓶品种不在预定的范围内");
        }
        if(this.variety == bluePotion){
            down1 = setup("/objects/potion_blue.png", gp.tileSize, gp.tileSize);
            description = "[蓝药水]\n饮用后将提高player的strength1点";
        }
        else if (this.variety == redPotion) {
            down1 = setup("/objects/potion_red.png", gp.tileSize, gp.tileSize);
            description = "[红药水]\n饮用后将立刻恢复player的\n血量2点";
        }

        image = down1;

        // solid area
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void use(){
        if(this.variety == bluePotion){
            gp.player.strength += 1;
        } else if (this.variety == redPotion) {
            gp.player.life += 2;
            if(gp.player.life >= gp.player.maxLife){
                gp.player.life = gp.player.maxLife;
            }
        }

    }
}
