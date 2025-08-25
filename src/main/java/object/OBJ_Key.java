package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OBJ_Key extends Entity {
    @Autowired
    private UtilityTool uTool;

    @Autowired
    public OBJ_Key(GamePanel gp){
        super();
        this.type = objectType;
        toUse = false;
        disappear = false;

        this.name = "Key";
        down1 = setup(uTool, "/objects/key.png", gp.tileSize, gp.tileSize);
        image = down1;
        collisionOn = true;
        description = "[钥匙]\n可以用来打开门\n钥匙的耐久只够打开一扇门";

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }


}
