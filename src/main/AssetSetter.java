package main;

import entity.NPC_Soldier;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    private GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;


    }


    public void setObject(){


    }

    public void setNPC(){
        gp.npcs[0] = new NPC_Soldier(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;

    }
}
