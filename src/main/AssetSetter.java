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
        gp.objects[0] = new OBJ_Door(gp);
        gp.objects[0].worldX = gp.tileSize * 21;
        gp.objects[0].worldY = gp.tileSize * 22;

        gp.objects[1] = new OBJ_Door(gp);
        gp.objects[1].worldX = gp.tileSize * 23;
        gp.objects[1].worldY = gp.tileSize * 25;
    }

    public void setNPC(){
        gp.npcs[0] = new NPC_Soldier(gp);
        gp.npcs[0].worldX = gp.tileSize * 21;
        gp.npcs[0].worldY = gp.tileSize * 21;

    }
}
